package com.group_cordillera.payment.controller;

import com.group_cordillera.payment.model.Payment;
import com.group_cordillera.payment.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment processPayment(@RequestBody Payment payment) {
        // Le asignamos la fecha y hora actual automáticamente al procesar
        payment.setFechaPago(LocalDateTime.now());
        return paymentRepository.save(payment);
    }
}
