package com.inditex.core.rest.utils;

import com.inditex.core.domain.model.Price;

import java.math.BigDecimal;

public class UtilsRestTest {

    public static Price buildPrice(){
        return new Price(35550, 1, "2024-01-01 00:00:00",
                "2024-12-31 23:59:59", new BigDecimal("35.50"), 2);
    }
}
