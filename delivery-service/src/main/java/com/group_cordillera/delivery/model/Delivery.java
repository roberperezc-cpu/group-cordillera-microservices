package com.group_cordillera.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ventaId; // Relaciona el despacho con la venta original
    private String direccionEnvio;
    private String tipoDespacho; // DESPACHO_DOMICILIO o RETIRO_TIENDA
    private String estadoEnvio; // PENDIENTE, EN_CAMINO, ENTREGADO
    private String empresaRepartidora; // Ejemplo: Starken, Chilexpress, CorreosChile
    private LocalDateTime fechaCreacion;
}