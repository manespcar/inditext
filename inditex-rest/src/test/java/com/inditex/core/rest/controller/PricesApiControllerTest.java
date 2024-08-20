package com.inditex.core.rest.controller;

import com.inditex.core.domain.model.Price;
import com.inditex.core.domain.usecase.FindPricesUseCase;
import com.inditex.core.rest.mapper.PricesApiMapper;
import com.inditex.core.rest.utils.UtilsRestTest;
import com.pagonxt.gtscore.rest.catalogue.client.model.PriceResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricesApiControllerTest {

    private PricesApiController controller;

    @Mock
    private FindPricesUseCase findPricesUseCase;
    @Mock
    private PricesApiMapper pricesApiMapper;

    @BeforeEach
    void setUp() {
        controller = new PricesApiController(findPricesUseCase, pricesApiMapper);
    }

    @Test
    void should_return200_when_thereAreResults() {
        //given
        final String date = "2024-01-01 12:00:00";
        final Integer brandId = 1;
        final Price price = UtilsRestTest.buildPrice();
        when(findPricesUseCase.filter(date, null, brandId)).thenReturn(Optional.of(price));
        when(pricesApiMapper.mapToDTO(price)).thenReturn(new PriceResponseDTO());
        //when
        final ResponseEntity<PriceResponseDTO> response = controller.queryPrices(date, null, brandId);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void should_return204_when_thereAreNotResults() {
        //given
        final String date = "2024-01-01 12:00:00";
        final Integer brandId = 1;
        when(findPricesUseCase.filter(date, null, brandId)).thenReturn(Optional.empty());
        //when
        final ResponseEntity<PriceResponseDTO> response = controller.queryPrices(date, null, brandId);
        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}