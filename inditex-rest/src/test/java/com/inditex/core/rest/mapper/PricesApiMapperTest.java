package com.inditex.core.rest.mapper;

import com.inditex.core.domain.model.Price;
import com.inditex.core.rest.utils.UtilsRestTest;
import com.pagonxt.gtscore.rest.catalogue.client.model.PriceResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PricesApiMapperTest {

    private PricesApiMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PricesApiMapper();
    }

    @Test
    void should_mapToDTO_when_map() {
        //given
        final Price price = UtilsRestTest.buildPrice();
        final PriceResponseDTO expected = new PriceResponseDTO();
        expected.setProductId(Long.valueOf(price.productId()));
        expected.setPrice(price.price());
        expected.setPriceList(price.priceList());
        expected.setBrandId(price.brandId());
        expected.setStartDate(price.startDate());
        expected.setEndDate(price.endDate());
        //when
        final PriceResponseDTO actual = mapper.mapToDTO(price);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}