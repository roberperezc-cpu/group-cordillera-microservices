package com.group_cordillera.discount.controller;

import com.group_cordillera.discount.model.Discount;
import com.group_cordillera.discount.repository.DiscountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {

    private final DiscountRepository discountRepository;

    public DiscountController(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Discount createDiscount(@RequestBody Discount discount) {
        return discountRepository.save(discount);
    }

    // Validar un cupón y devolver su porcentaje
    @GetMapping("/validate")
    public ResponseEntity<Discount> validateCoupon(@RequestParam String code) {
        return discountRepository.findByCodigoCuponAndActivoTrue(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}