package com.example.payment_service.service;

import com.example.payment_service.domain.PaymentMethod;
import com.example.payment_service.domain.PaymentOrderStatus;
import com.example.payment_service.modal.PaymentOrder;
import com.example.payment_service.payload.BookingDto;
import com.example.payment_service.payload.PaymentLinkResponse;
import com.example.payment_service.payload.UserDto;
import com.example.payment_service.repository.PaymentOrderRepository;

import com.stripe.Stripe;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
  @Value("${stripe.api.key}")
  private String stripeApiKey;
  @Autowired
  private PaymentOrderRepository paymentRepository;
  @Override
  public PaymentLinkResponse createOrder(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethod) {
    if (bookingDto == null || userDto == null)
      throw new IllegalArgumentException("Invalid booking or user");

    Long amount = (long) Math.round(bookingDto.getTotalPrice() * 100);

    PaymentOrder paymentOrder = new PaymentOrder();
    paymentOrder.setAmount(amount);
    paymentOrder.setPaymentMethod(paymentMethod);
    paymentOrder.setBookingId(bookingDto.getId());
    paymentOrder.setUserId(userDto.getId());
    paymentOrder.setSalonId(bookingDto.getSalonId());
    paymentOrder.setStatus(PaymentOrderStatus.PENDING);

    PaymentOrder saved = paymentRepository.save(paymentOrder);

    PaymentLinkResponse response = new PaymentLinkResponse();
    if (paymentMethod.equals(PaymentMethod.STRIPE)) {
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
  public Boolean processPayment(PaymentOrder order, String paymentId, String paymentLinkId) {
    return null;
  }


}