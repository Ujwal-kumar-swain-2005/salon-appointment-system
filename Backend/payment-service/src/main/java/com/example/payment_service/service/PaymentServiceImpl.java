package com.example.payment_service.service;

import com.example.payment_service.domain.PaymentOrderStatus;
import com.example.payment_service.modal.PaymentOrder;
import com.example.payment_service.payload.BookingDto;
import com.example.payment_service.payload.PaymentLinkResponse;
import com.example.payment_service.payload.UserDto;
import com.example.payment_service.repository.PaymentOrderRepository;
import com.paypal.base.rest.APIContext;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.example.payment_service.domain.PaymentMethod;
import com.stripe.Stripe;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentOrderRepository paymentRepository;
    @Autowired
    private APIContext apiContext;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${razorpay.api.key}")
    private String razorApiKey;

    @Value("${razorpay.secret.key}")
    private String razorSecretKey;

    @Override
    public PaymentLinkResponse createOrder(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethod) {

        if (bookingDto == null || userDto == null)
            throw new IllegalArgumentException("Invalid booking or user");

        Long amount = (long) Math.round(bookingDto.getTotalPrice() * 100);

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setBookingId(bookingDto.getId());
        paymentOrder.setSalonId(bookingDto.getSalonId());
        paymentOrder.setStatus(PaymentOrderStatus.PENDING);

        PaymentOrder saved = paymentRepository.save(paymentOrder);

        PaymentLinkResponse response = new PaymentLinkResponse();

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            try {
                PaymentLink link = createrRazorPayPaymentLink(userDto, amount, saved.getId());
                response.setPaymentLinkId(link.get("id"));
                response.setPaymentLinkUrl(link.get("short_url"));

                saved.setPaymentLinkId(link.get("id"));
                paymentRepository.save(saved);
            } catch (Exception e) {
                saved.setStatus(PaymentOrderStatus.FAILED);
                paymentRepository.save(saved);
                throw new RuntimeException("Failed to create RazorPay link", e);
            }
            return response;
        }

        else if (paymentMethod.equals(PaymentMethod.STRIPE)) {
            try {
                String url = createStripePaymentLink(userDto, amount, saved.getId());
                response.setPaymentLinkUrl(url);
            } catch (Exception e) {
                saved.setStatus(PaymentOrderStatus.FAILED);
                paymentRepository.save(saved);
                throw new RuntimeException("Failed to create Stripe link", e);
            }
            return response;
        }

        else if (paymentMethod.equals(PaymentMethod.PAYPAL)) {
            try {
                String approveLink = createPayPalPaymentLink(userDto, amount, saved.getId());
                response.setPaymentLinkUrl(approveLink);
            } catch (Exception e) {
                saved.setStatus(PaymentOrderStatus.FAILED);
                paymentRepository.save(saved);
                throw new RuntimeException("Failed to create PayPal link", e);
            }
            return response;
        }

        throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
    }



    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        PaymentOrder order = paymentRepository.findById(id).orElse(null);
        if (order == null) {
            throw new Exception("Payment order not found with id: " + id);
        }
        return order;
    }
    @Override
    public PaymentOrder getOrderByPaymentLinkId(String paymentLinkId) {
        return paymentRepository.findByPaymentLinkId(paymentLinkId);
    }
    @Override
    public PaymentLink createrRazorPayPaymentLink(UserDto userDto, Long amount, Long orderId) {
        Long amountPayment = (long) Math.round(amount * 100);
        try {
            RazorpayClient client = new RazorpayClient(razorApiKey, razorSecretKey);

            JSONObject request = new JSONObject();
            request.put("amount", amountPayment);
            request.put("currency", "INR");
            request.put("description", "Payment for Order ID: " + orderId);

            JSONObject customer = new JSONObject();
            customer.put("name", userDto.getFullName());
            customer.put("email", userDto.getEmail());
            request.put("customer", customer);

            request.put("notify", new JSONObject().put("sms", true).put("email", true));
            request.put("reminder_enable", true);
            request.put("callback_url", "http://localhost:3000/payment-sucess/" + orderId);
            request.put("callback_method", "get");

            PaymentLink link = client.paymentLink.create(request);
            return link;
        } catch (Exception e) {
            throw new RuntimeException("Error creating RazorPay payment link", e);
        }
    }

    @Override
    public String createStripePaymentLink(UserDto userDto, Long amountInCents, Long orderId) {
        Stripe.apiKey = stripeApiKey;

        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl("http://localhost:3000/payment-sucess/" + orderId)
                            .setCancelUrl("http://localhost:3000/payment-failed/" + orderId)
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("usd")
                                                            .setUnitAmount(amountInCents)
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData
                                                                            .builder()
                                                                            .setName("Payment for Order ID: " + orderId)
                                                                            .build())
                                                            .build())
                                            .build())
                            .putMetadata("orderId", orderId.toString())
                            .putMetadata("userEmail", userDto.getEmail())
                            .build();

            Session session = Session.create(params);
            return session.getUrl();

        } catch (Exception e) {
            throw new RuntimeException("Error creating Stripe Checkout Session", e);
        }
    }
      @Override
      public String createPayPalPaymentLink(UserDto userDto, Long amountInCents, Long orderId) {
          try {
              double amount = amountInCents / 100.0;

              Amount paypalAmount = new Amount();
              paypalAmount.setCurrency("USD");
              paypalAmount.setTotal(String.format("%.2f", amount));

              Transaction transaction = new Transaction();
              transaction.setDescription("Payment for Order ID: " + orderId);
              transaction.setAmount(paypalAmount);

              Payer payer = new Payer();
              payer.setPaymentMethod("paypal");

              Payment payment = new Payment();
              payment.setIntent("sale");
              payment.setPayer(payer);
              payment.setTransactions(List.of(transaction));

              RedirectUrls redirectUrls = new RedirectUrls();
              redirectUrls.setCancelUrl("http://localhost:3000/payment-failed/" + orderId);
              redirectUrls.setReturnUrl("http://localhost:3000/payment-sucess/" + orderId);

              payment.setRedirectUrls(redirectUrls);

              Payment createdPayment = payment.create(apiContext);

              for (Links link : createdPayment.getLinks()) {
                  if (link.getRel().equals("approval_url")) {
                      return link.getHref();
                  }
              }

              throw new RuntimeException("PayPal approval link not found!");

          } catch (Exception e) {
              throw new RuntimeException("Error creating PayPal payment", e);
          }
      }

    @Override
    public Boolean processPayment(PaymentOrder order, String paymentId, String paymentLinkId) {

        if (order.getStatus() != PaymentOrderStatus.PENDING) {
            return false;
        }

        try {
            // -------------------------//
            // 1. Razorpay
            // -------------------------//
            if (order.getPaymentMethod() == PaymentMethod.RAZORPAY) {
                RazorpayClient client = new RazorpayClient(razorApiKey, razorSecretKey);
                com.razorpay.Payment payment = client.payments.fetch(paymentId);

                String status = payment.get("status");
                if ("captured".equals(status)) {
                    order.setStatus(PaymentOrderStatus.COMPLETED);
                    paymentRepository.save(order);
                    return true;
                } else {
                    order.setStatus(PaymentOrderStatus.FAILED);
                    paymentRepository.save(order);
                    return false;
                }
            }

            // -------------------------//
            // 2. Stripe
            // -------------------------//
            if (order.getPaymentMethod() == PaymentMethod.STRIPE) {
                Stripe.apiKey = stripeApiKey;

                Session session = Session.retrieve(paymentId);
                // session.getPaymentStatus()  = "paid" or "unpaid"
                if ("paid".equals(session.getPaymentStatus())) {
                    order.setStatus(PaymentOrderStatus.COMPLETED);
                    paymentRepository.save(order);
                    return true;
                } else {
                    order.setStatus(PaymentOrderStatus.FAILED);
                    paymentRepository.save(order);
                    return false;
                }
            }

            // -------------------------//
            // 3. PayPal
            // -------------------------//
            if (order.getPaymentMethod() == PaymentMethod.PAYPAL) {

                Payment payment = Payment.get(apiContext, paymentId);

                String state = payment.getState(); // "approved" | "failed"

                if ("approved".equalsIgnoreCase(state)) {
                    order.setStatus(PaymentOrderStatus.COMPLETED);
                    paymentRepository.save(order);
                    return true;
                } else {
                    order.setStatus(PaymentOrderStatus.FAILED);
                    paymentRepository.save(order);
                    return false;
                }
            }

        } catch (Exception e) {
            order.setStatus(PaymentOrderStatus.FAILED);
            paymentRepository.save(order);
            return false;
        }

        return false;
    }
}
