package com.group_cordillera.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class InventoryClient {

    private final WebClient webClient;

    public InventoryClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8082/api/v1/inventory")
                .build();
    }


    public boolean consultarStock(Long productoId, Integer cantidad) {
        log.info("[WEBCLIENT] Solicitando validación de stock al servicio de Inventarios externa.");
        try {
            Boolean tieneStock = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/check")
                            .queryParam("productoId", productoId)
                            .queryParam("cantidad", cantidad)
                            .build())
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block(); // Consumo síncrono controlado

            return tieneStock != null && tieneStock;
        } catch (Exception e) {
            log.error("[WEBCLIENT ERROR] No se pudo comunicar con el servicio de inventario: {}", e.getMessage());
            // Si el servicio está caído, por seguridad de negocio denegamos la venta
            return false;
        }
    }
}
