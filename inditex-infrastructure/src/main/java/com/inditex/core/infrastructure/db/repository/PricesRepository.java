package com.inditex.core.infrastructure.db.repository;

import com.inditex.core.infrastructure.db.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PricesRepository extends JpaRepository<PriceEntity, Integer> {

    @Query("SELECT p FROM PriceEntity p " +
            "WHERE (:product is null OR p.product.id = :product) " +
            "AND (:brand is null OR p.brand.id = :brand) " +
            "AND (:dateTime >= p.startDate AND :dateTime <= p.endDate) " +
            "ORDER BY p.priority DESC LIMIT 1")
    Optional<PriceEntity> findPriceWithFilters(LocalDateTime dateTime, Long product, Integer brand);
}
