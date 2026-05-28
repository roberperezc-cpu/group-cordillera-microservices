
package com.group_cordillera.delivery.controller;

import com.group_cordillera.delivery.model.Delivery;
import com.group_cordillera.delivery.repository.DeliveryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryRepository deliveryRepository;

    public DeliveryController(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }


    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery createDelivery(@RequestBody Delivery delivery) {
        delivery.setFechaCreacion(LocalDateTime.now());
        if (delivery.getEstadoEnvio() == null) {
            delivery.setEstadoEnvio("PENDIENTE");
        }
        return deliveryRepository.save(delivery);
    }
}