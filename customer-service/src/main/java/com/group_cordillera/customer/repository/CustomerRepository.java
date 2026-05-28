package com.group_cordillera.customer.repository;

import com.group_cordillera.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //método para buscar clientes por su identificación única
    Optional<Customer> findByRut(String rut);
}
