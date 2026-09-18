package com.enigmacamp.chall_koperasikita.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stock;
    private boolean isAvailable;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
    private String deletedAt;
    private String deletedBy;
}
