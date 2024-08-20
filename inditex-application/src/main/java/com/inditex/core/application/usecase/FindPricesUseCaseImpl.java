package com.inditex.core.application.usecase;

import com.inditex.core.domain.model.Price;
import com.inditex.core.domain.repository.FindPricesRepository;
import com.inditex.core.domain.usecase.FindPricesUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class FindPricesUseCaseImpl implements FindPricesUseCase {

    private final FindPricesRepository findPricesRepository;

    public FindPricesUseCaseImpl(final FindPricesRepository findPricesRepository) {
        this.findPricesRepository = findPricesRepository;
    }

    @Override
    public Optional<Price> filter(final String date, final Long productId, final Integer brandId) {
        return findPricesRepository.filter(date, productId, brandId);
    }
}
