package com.inditex.core.domain.repository;

import com.inditex.core.domain.model.Price;

import java.util.Optional;

public interface FindPricesRepository {
    Optional<Price> filter(String date, Long productId, Integer brandId);
}
