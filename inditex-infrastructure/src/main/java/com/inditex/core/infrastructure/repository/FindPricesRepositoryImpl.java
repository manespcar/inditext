package com.inditex.core.infrastructure.repository;

import com.inditex.core.domain.model.Price;
import com.inditex.core.domain.repository.FindPricesRepository;
import com.inditex.core.domain.utils.ConvertDates;
import com.inditex.core.infrastructure.db.entity.PriceEntity;
import com.inditex.core.infrastructure.db.repository.PricesRepository;
import com.inditex.core.infrastructure.mapper.PricesDomainMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
class FindPricesRepositoryImpl implements FindPricesRepository {

    private final PricesRepository pricesRepository;
    private final PricesDomainMapper pricesDomainMapper;

    public FindPricesRepositoryImpl(final PricesRepository pricesRepository,
                                    final PricesDomainMapper pricesDomainMapper) {
        this.pricesRepository = pricesRepository;
        this.pricesDomainMapper = pricesDomainMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Price> filter(final String date, final Long productId, final Integer brandId) {
        final Optional<PriceEntity> priceWithFilters = pricesRepository
                .findPriceWithFilters(ConvertDates.convertToLocalDateTime(date), productId, brandId);
        return priceWithFilters.map(pricesDomainMapper::mapToDomain);
    }
}
