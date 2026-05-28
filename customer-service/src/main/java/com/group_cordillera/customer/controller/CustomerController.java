package com.group_cordillera.customer.controller;

import com.group_cordillera.customer.model.Customer;
import com.group_cordillera.customer.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // 1. Obtener todos los clientes
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // 2. Crear un nuevo cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    // 3. Buscar cliente por RUT
    @GetMapping("/search")
    public ResponseEntity<Customer> getCustomerByRut(@RequestParam String rut) {
        return customerRepository.findByRut(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
