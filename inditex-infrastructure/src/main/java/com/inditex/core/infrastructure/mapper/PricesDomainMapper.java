package com.inditex.core.infrastructure.mapper;

import com.inditex.core.domain.model.Price;
import com.inditex.core.domain.utils.ConvertDates;
import com.inditex.core.infrastructure.db.entity.PriceEntity;
import org.springframework.stereotype.Component;

@Component
public class PricesDomainMapper {

    public Price mapToDomain(final PriceEntity priceEntity) {
        return new Price(priceEntity.getProduct().getId(),
                priceEntity.getBrand().getId(),
                ConvertDates.convertToString(priceEntity.getStartDate()),
                ConvertDates.convertToString(priceEntity.getEndDate()),
                priceEntity.getPrice(),
                priceEntity.getPriceList());
    }
}
