package com.group_cordillera.controller;

import com.group_cordillera.dto.VentaRequestDTO;
import com.group_cordillera.dto.DetalleVentaRequestDTO;
import com.group_cordillera.model.DetalleVenta;
import com.group_cordillera.model.Venta;
import com.group_cordillera.service.PostService;
import com.group_cordillera.service.SalesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/sales")
@CrossOrigin(origins = "*")
@Slf4j
@Tag(name = "Módulo de Ventas", description = "Endpoints globales para procesar e integrar boletas de Grupo Cordillera")
public class SalesController {

    @Autowired
    private SalesService service;

    @Autowired
    private PostService postService;

    public List<Venta> listar() { return service.getVentas(); }

    @GetMapping
    @Operation(summary = "Listar todas las ventas", description = "Retorna una colección HATEOAS con todas las boletas unificadas de las sucursales")
    public ResponseEntity<CollectionModel<EntityModel<Venta>>> listarDenuevo(){
        log.info("[CONTROLLER] Endpoint GET '/' invocado para listar todas las ventas.");
        List<Venta> registro = service.getVentas();

        if(registro.isEmpty()) {
            log.warn("[CONTROLLER] No se encontraron registros de ventas. Retornando HTTP 204.");
            return ResponseEntity.noContent().build();
        }

        // Aplicamos HATEOAS convirtiendo cada venta en un EntityModel con su link propio
        List<EntityModel<Venta>> ventasModel = registro.stream()
                .map(venta -> EntityModel.of(venta,
                        linkTo(methodOn(SalesController.class).buscarPorId(venta.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Venta>> collectionModel = CollectionModel.of(ventasModel,
                linkTo(methodOn(SalesController.class).listarDenuevo()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venta por ID", description = "Obtiene los detalles de una boleta específica incluyendo enlaces RESTful (HATEOAS)")
    public ResponseEntity<EntityModel<Venta>> buscarPorId(@PathVariable Long id){
        log.info("[CONTROLLER] Endpoint GET '/{}' invocado.", id);
        Optional<Venta> registro = service.getVenta(id);

        if (registro.isPresent()) {
            Venta venta = registro.get();
            // Creamos la estructura HATEOAS
            EntityModel<Venta> modelo = EntityModel.of(venta,
                    linkTo(methodOn(SalesController.class).buscarPorId(id)).withSelfRel(),
                    linkTo(methodOn(SalesController.class).listarDenuevo()).withRel("lista-completa-ventas"));
            return ResponseEntity.ok(modelo);
        } else {
            log.warn("[CONTROLLER] Venta con ID {} no encontrada. Retornando HTTP 404.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Ingestar una nueva venta", description = "Registra una boleta mapeando automáticamente sus detalles hacia la base de datos")
    public ResponseEntity<Venta> agregarVenta(@Valid @RequestBody VentaRequestDTO dto){
        log.info("[CONTROLLER] Recibida petición POST para ingestar boleta: {}", dto.getCodigoBoleta());
        Venta nuevaVenta = convertirDtoAEntidad(dto);
        Venta nuevoRegistro = service.saveVenta(nuevaVenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRegistro);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar una venta existente", description = "Actualiza los campos de cabecera o montos de una venta según su ID")
    public ResponseEntity<Venta> editarRegistro(@Valid @RequestBody VentaRequestDTO dto, @PathVariable Long id){
        log.info("[CONTROLLER] Recibida petición PUT para modificar ID: {}", id);
        Optional<Venta> existe = service.getVenta(id);

        if (existe.isEmpty()) {
            log.warn("[CONTROLLER] Error al editar. El ID {} no existe.", id);
            return ResponseEntity.notFound().build();
        }

        Venta registro = convertirDtoAEntidad(dto);
        registro.setId(id);
        Venta actualizado = service.saveVenta(registro);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar venta", description = "Borra físicamente el registro de la venta y sus detalles en cascada")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Long id){
        log.info("[CONTROLLER] Recibida petición DELETE para ID: {}", id);
        try {
            service.deleteVenta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            log.error("[CONTROLLER ERROR] Falló la eliminación del ID {}. Motivo: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/post")
    @Operation(summary = "Prueba de integración", description = "Simula flujos del PostService heredados del proyecto base")
    public ResponseEntity<Map<String, Object>> registroConPost(@PathVariable Long id) {
        Optional<Venta> registroOpt = service.getVenta(id);

        if (registroOpt.isEmpty())
            return ResponseEntity.notFound().build();

        Venta registro = registroOpt.get();
        postService.obtenerPost();

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("registro", registro.getCodigoBoleta());
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar venta alternativo", description = "Ruta secundaria para almacenamiento persistente directo")
    public ResponseEntity<Venta> guardar(@Valid @RequestBody VentaRequestDTO dto) {
        Venta nuevaVenta = convertirDtoAEntidad(dto);
        return ResponseEntity.ok(service.saveVenta(nuevaVenta));
    }

    // MÉTODOS DE CONVERSIÓN (DTO -> ENTIDAD)
    private Venta convertirDtoAEntidad(VentaRequestDTO dto) {
        Venta venta = new Venta();
        venta.setCodigoBoleta(dto.getCodigoBoleta());
        venta.setSucursal(dto.getSucursal());
        venta.setTotal(dto.getTotal());
        venta.setCanal(dto.getCanal());
        venta.setFechaVenta(LocalDateTime.now());

        if (dto.getDetalles() != null) {
            List<DetalleVenta> detallesEn = dto.getDetalles().stream()
                    .map(this::convertirDetalleDtoAEntidad)
                    .collect(Collectors.toList());
            venta.setDetalles(detallesEn);
        }
        return venta;
    }

    private DetalleVenta convertirDetalleDtoAEntidad(DetalleVentaRequestDTO dto) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProductoId(dto.getProductoId());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        return detalle;
    }
}

