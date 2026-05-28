package com.group_cordillera.discount.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoCupon; // Ejemplo: CORD10, LIQUIDACION
    private Double porcentajeDescuento; // Ejemplo: 10.0 para un 10%
    private Boolean activo;
}