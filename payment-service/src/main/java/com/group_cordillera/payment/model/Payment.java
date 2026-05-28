package com.group_cordillera.payment.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ventaId; // Relaciona el pago con la boleta guardada en ventas
    private Double monto;
    private String metodoPago; // EFECTIVO, TARJETA_DEBITO, TARJETA_CREDITO
    private String estado; // APROBADO, RECHAZADO, PENDIENTE
    private LocalDateTime fechaPago;
}
