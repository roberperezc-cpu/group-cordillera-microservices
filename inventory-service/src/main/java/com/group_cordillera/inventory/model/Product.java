package com.group_cordillera.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoBarra;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;
}