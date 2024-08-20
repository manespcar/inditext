package com.inditex.core.rest.mapper;

import com.inditex.core.domain.model.Price;
import com.pagonxt.gtscore.rest.catalogue.client.model.PriceResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PricesApiMapper {

    public PriceResponseDTO mapToDTO(final Price price) {
        PriceResponseDTO priceResponseDTO = new PriceResponseDTO();
        priceResponseDTO.setProductId(Long.valueOf(price.productId()));
        priceResponseDTO.setPrice(price.price());
        priceResponseDTO.setPriceList(price.priceList());
        priceResponseDTO.setBrandId(price.brandId());
        priceResponseDTO.setStartDate(price.startDate());
        priceResponseDTO.setEndDate(price.endDate());
        return priceResponseDTO;
    }
}
