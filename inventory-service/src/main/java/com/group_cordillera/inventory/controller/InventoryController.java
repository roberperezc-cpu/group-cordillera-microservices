package com.group_cordillera.inventory.controller;

import com.group_cordillera.inventory.model.Product;
import com.group_cordillera.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inventory")
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Módulo de Inventario", description = "Endpoints para la gestión, control de existencias y stock en tiempo real de Grupo Cordillera")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna la lista completa de productos disponibles en el inventario")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> obtenerTodos() {
        log.info("[INVENTORY CONTROLLER] Listando todos los productos de la base de datos");

        // 1. Buscamos todos los productos usando tu servicio (ajusta el método si se llama findAll o listar)
        List<Product> productos = inventoryService.obtenerTodos();

        // 2. Transformamos cada producto en un EntityModel (puedes agregarle links individuales si quieres)
        List<EntityModel<Product>> productosModel = productos.stream()
                .map(producto -> EntityModel.of(producto))
                .collect(Collectors.toList());

        // 3. Creamos el contenedor de la colección con su link self
        CollectionModel<EntityModel<Product>> modelo = CollectionModel.of(productosModel,
                linkTo(methodOn(InventoryController.class).obtenerTodos()).withSelfRel());

        return ResponseEntity.ok(modelo);
    }

    @GetMapping("/check")
    @Operation(summary = "Verificar disponibilidad de stock", description = "Valida si existe la cantidad solicitada de un producto determinado para autorizar el flujo de venta")
    public ResponseEntity<EntityModel<Map<String, Object>>> verificarStock(
            @RequestParam Long productoId,
            @RequestParam Integer cantidad) {

        log.info("[INVENTORY CONTROLLER] Verificando disponibilidad. Producto ID: {}, Cantidad solicitada: {}", productoId, cantidad);
        boolean tieneStock = inventoryService.validarStock(productoId, cantidad);

        // Creamos una estructura de mapa para representar la respuesta de forma clara
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("productoId", productoId);
        respuesta.put("cantidadSolicitada", cantidad);
        respuesta.put("disponible", tieneStock);

        // Añadimos soporte hipermedia (HATEOAS)
        EntityModel<Map<String, Object>> modelo = EntityModel.of(respuesta,
                linkTo(methodOn(InventoryController.class).verificarStock(productoId, cantidad)).withSelfRel());

        return ResponseEntity.ok(modelo);
    }
}