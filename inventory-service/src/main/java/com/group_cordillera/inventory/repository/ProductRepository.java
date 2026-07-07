package com.group_cordillera.inventory.repository;
import com.group_cordillera.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // No necesitas escribir ningún método aquí adentro, JpaRepository ya incluye el .findAll() de forma nativa.
}
