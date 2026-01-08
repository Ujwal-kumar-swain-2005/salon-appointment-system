package com.example.payment_service.controller;

import com.example.payment_service.payload.BookingDto;
import com.example.payment_service.payload.UserDto;
import com.example.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.payment_service.domain.PaymentMethod;
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody BookingDto bookingDto, @RequestParam PaymentMethod paymentMethod) {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFullName("John Doe");
        userDto.setEmail("JohnDoe@gmail.com");

        var res = paymentService.createOrder(userDto, bookingDto, paymentMethod);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentOrderById(@PathVariable Long id) throws Exception {
        var res = paymentService.getPaymentOrderById(id);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<?> proceedPayment(@RequestParam String paymentId, @RequestParam String paymentLinkId) throws Exception {
        var order = paymentService.getOrderByPaymentLinkId(paymentLinkId);
        var res = paymentService.processPayment(order, paymentId, paymentLinkId);
        return ResponseEntity.ok(res);
    }
}