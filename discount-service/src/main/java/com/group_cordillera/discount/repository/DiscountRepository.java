package com.group_cordillera.discount.repository;

import com.group_cordillera.discount.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    // Para buscar si un cupón existe y está activo
    Optional<Discount> findByCodigoCuponAndActivoTrue(String codigoCupon);
}
