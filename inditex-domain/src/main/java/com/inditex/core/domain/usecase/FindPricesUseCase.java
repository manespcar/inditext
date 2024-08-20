package com.inditex.core.domain.usecase;

import com.inditex.core.domain.model.Price;

import java.util.Optional;

public interface FindPricesUseCase {
    Optional<Price> filter(String date, Long productId, Integer brandId);
}
