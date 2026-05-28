package com.group_cordillera.customer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rut;
    private String nombre;
    private String email;
    private String telefono;
}
