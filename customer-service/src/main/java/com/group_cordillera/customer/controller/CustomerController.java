package com.group_cordillera.customer.controller;

import com.group_cordillera.customer.model.Customer;
import com.group_cordillera.customer.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Módulo de Clientes", description = "Endpoints para la gestión, registro y consulta por RUT de clientes de Grupo Cordillera")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // 1. Obtener todos los clientes (Modificado para HATEOAS)
    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Retorna una colección enriquecida con enlaces hipermedia de todos los clientes registrados")
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Convertimos cada cliente en un EntityModel con su enlace dinámico de búsqueda por RUT
        List<EntityModel<Customer>> customerModels = customers.stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(CustomerController.class).getCustomerByRut(customer.getRut())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Customer>> collectionModel = CollectionModel.of(customerModels,
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    // 2. Crear un nuevo cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar un cliente", description = "Crea un nuevo perfil de cliente en la base de datos centralizada")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    // 3. Buscar cliente por RUT (Modificado para HATEOAS)
    @GetMapping("/search")
    @Operation(summary = "Buscar cliente por RUT", description = "Obtiene los datos de un cliente específico mediante su RUT, incluyendo enlaces HATEOAS")
    public ResponseEntity<EntityModel<Customer>> getCustomerByRut(@RequestParam String rut) {
        return customerRepository.findByRut(rut)
                .map(customer -> {
                    EntityModel<Customer> modelo = EntityModel.of(customer,
                            linkTo(methodOn(CustomerController.class).getCustomerByRut(rut)).withSelfRel(),
                            linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("lista-completa-clientes"));
                    return ResponseEntity.ok(modelo);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
