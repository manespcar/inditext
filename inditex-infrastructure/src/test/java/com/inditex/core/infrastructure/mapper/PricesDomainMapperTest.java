package com.inditex.core.infrastructure.mapper;

import com.inditex.core.domain.model.Price;
import com.inditex.core.domain.utils.ConvertDates;
import com.inditex.core.infrastructure.db.entity.BrandEntity;
import com.inditex.core.infrastructure.db.entity.CurrencyEntity;
import com.inditex.core.infrastructure.db.entity.PriceEntity;
import com.inditex.core.infrastructure.db.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PricesDomainMapperTest {

    private PricesDomainMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PricesDomainMapper();
    }

    @Test
    void should_mapToDomain_when_map() {
        //given
        final BrandEntity brand = new BrandEntity();
        brand.setId(1);
        final CurrencyEntity currency = new CurrencyEntity();
        currency.setId("EUR");
        final ProductEntity product = new ProductEntity();
        product.setId(12345);

        final String startDate = "2024-01-01 00:00:00";
        final String endDate = "2024-12-31 23:59:59";

        final PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(1);
        priceEntity.setPriceList(2);
        priceEntity.setPrice(new BigDecimal("25.45"));
        priceEntity.setBrand(brand);
        priceEntity.setPriority(0);
        priceEntity.setCurrency(currency);
        priceEntity.setProduct(product);
        priceEntity.setStartDate(ConvertDates.convertToLocalDateTime(startDate));
        priceEntity.setEndDate(ConvertDates.convertToLocalDateTime(endDate));

        final Price expected = new Price(priceEntity.getProduct().getId(),
                priceEntity.getBrand().getId(),
                startDate,
                endDate,
                priceEntity.getPrice(),
                priceEntity.getPriceList());
        //when
        final Price actual = mapper.mapToDomain(priceEntity);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}