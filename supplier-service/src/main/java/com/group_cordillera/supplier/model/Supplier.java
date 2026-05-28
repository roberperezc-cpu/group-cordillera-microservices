package com.group_cordillera.supplier.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rutEmpresa; // RUT o NIT del proveedor
    private String razonSocial; // Nombre de la empresa
    private String contactoNombre; // Nombre del ejecutivo de ventas
    private String telefono;
    private String rubro; // Ejemplo: BEBIDAS, ABARROTES, ELECTRÓNICA
}