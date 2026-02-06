package com.example.payment_service.service;

import com.example.payment_service.modal.PaymentOrder;
import com.example.payment_service.payload.BookingDto;
import com.example.payment_service.payload.PaymentLinkResponse;
import com.example.payment_service.payload.UserDto;
import com.example.payment_service.domain.PaymentMethod;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentLinkResponse createOrder(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getOrderByPaymentLinkId(String paymentLinkId);

    String createStripePaymentLink(UserDto userDto, Long amount, Long orderId);
    Boolean processPayment(PaymentOrder order, String paymentId, String paymentLinkId);
}