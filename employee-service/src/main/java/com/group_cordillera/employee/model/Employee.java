package com.group_cordillera.employee.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rut; // O identificación
    private String nombre;
    private String cargo; // Ejemplo: VENDEDOR, CAJERO, ADMINISTRADOR
    private String email;
}
