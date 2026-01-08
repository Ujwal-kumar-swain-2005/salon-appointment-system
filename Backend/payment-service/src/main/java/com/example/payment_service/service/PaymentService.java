package com.example.payment_service.service;

import com.example.payment_service.modal.PaymentOrder;
import com.example.payment_service.payload.BookingDto;
import com.example.payment_service.payload.PaymentLinkResponse;
import com.example.payment_service.payload.UserDto;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.example.payment_service.domain.PaymentMethod;
public interface PaymentService {

    PaymentLinkResponse createOrder(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getOrderByPaymentLinkId(String paymentLinkId);

    PaymentLink createrRazorPayPaymentLink(UserDto userDto, Long amount, Long orderId);

    String createStripePaymentLink(UserDto userDto, Long amount, Long orderId);

    String createPayPalPaymentLink(UserDto userDto, Long amount, Long orderId);

    Boolean processPayment(PaymentOrder order, String paymentId, String paymentLinkId) throws RazorpayException;

}