package com.inditex.core.rest.controller;

import com.inditex.core.domain.model.Price;
import com.inditex.core.domain.usecase.FindPricesUseCase;
import com.inditex.core.rest.mapper.PricesApiMapper;
import com.pagonxt.gtscore.rest.catalogue.client.api.PricesApi;
import com.pagonxt.gtscore.rest.catalogue.client.model.PriceResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/prices_management")
class PricesApiController implements PricesApi {

    private final FindPricesUseCase findPricesUseCase;
    private final PricesApiMapper pricesApiMapper;

    public PricesApiController(final FindPricesUseCase findPricesUseCase,
                               final PricesApiMapper pricesApiMapper) {
        this.findPricesUseCase = findPricesUseCase;
        this.pricesApiMapper = pricesApiMapper;
    }

    @Override
    public ResponseEntity<PriceResponseDTO> queryPrices(final String date,
                                                        final Long productId,
                                                        final Integer brandId) {
        final Optional<Price> optionalPrice = this.findPricesUseCase.filter(date, productId, brandId);
        return optionalPrice.map(price -> ResponseEntity.ok(pricesApiMapper.mapToDTO(price)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
