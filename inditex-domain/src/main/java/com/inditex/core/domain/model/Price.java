package com.inditex.core.domain.model;

import java.math.BigDecimal;

public record Price(Integer productId, Integer brandId, String startDate,
                    String endDate, BigDecimal price, Integer priceList) {
}
