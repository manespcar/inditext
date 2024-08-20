package com.inditex.core.infrastructure.repository;

import com.inditex.core.domain.repository.FindPricesRepository;
import com.inditex.core.infrastructure.db.entity.BrandEntity;
import com.inditex.core.infrastructure.db.entity.CurrencyEntity;
import com.inditex.core.infrastructure.db.entity.PriceEntity;
import com.inditex.core.infrastructure.db.entity.ProductEntity;
import com.inditex.core.infrastructure.db.repository.PricesRepository;
import com.inditex.core.infrastructure.mapper.PricesDomainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindPricesRepositoryTest {

    private FindPricesRepository repository;

    @Mock
    private PricesRepository pricesRepository;
    @Mock
    private PricesDomainMapper pricesDomainMapper;

    @BeforeEach
    void setUp() {
        repository = new FindPricesRepositoryImpl(pricesRepository, pricesDomainMapper);
    }

    @Test
    void should_findPricesWithFilters_when_filter() {
        //given
        final String date = "2024-01-01 12:00:00";
        final Integer brandId = 1;
        final Long productId = 12345L;
        final PriceEntity priceEntity = buildPriceEntity();
        when(pricesRepository.findPriceWithFilters(any(LocalDateTime.class), eq(productId), eq(brandId)))
                .thenReturn(Optional.of(priceEntity));
        //when
        repository.filter(date, productId, brandId);
        //then
        verify(pricesDomainMapper).mapToDomain(priceEntity);
    }

    private static PriceEntity buildPriceEntity() {
        BrandEntity brand = new BrandEntity();
        brand.setId(1);
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId("EUR");
        ProductEntity product = new ProductEntity();
        product.setId(12345);

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(1);
        priceEntity.setPriceList(2);
        priceEntity.setPrice(new BigDecimal("25.45"));
        priceEntity.setBrand(brand);
        priceEntity.setPriority(0);
        priceEntity.setCurrency(currency);
        priceEntity.setProduct(product);
        priceEntity.setStartDate(LocalDateTime.now());
        priceEntity.setEndDate(LocalDateTime.now().plusDays(30));
        return priceEntity;
    }
}