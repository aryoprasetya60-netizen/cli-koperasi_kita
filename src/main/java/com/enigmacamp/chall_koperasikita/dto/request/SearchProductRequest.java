package com.enigmacamp.chall_koperasikita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductRequest {
    private String name;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minStock;
    private Boolean isAvailable;

    // pagination dan sorting
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
}
