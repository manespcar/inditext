package com.inditex.core.application.usecase;

import com.inditex.core.domain.repository.FindPricesRepository;
import com.inditex.core.domain.usecase.FindPricesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FindPricesUseCaseTest {

    private FindPricesUseCase useCase;

    @Mock
    private FindPricesRepository findPricesRepository;

    @BeforeEach
    void setUp() {
        useCase = new FindPricesUseCaseImpl(findPricesRepository);
    }

    @Test
    void should_getPrices_when_filter() {
        //given
        final String date = "2024-01-01 12:00:00";
        final Integer brandId = 1;
        final Long productId = 12345L;
        //when
        useCase.filter(date, productId, brandId);
        //then
        verify(findPricesRepository).filter(date, productId, brandId);
    }
}