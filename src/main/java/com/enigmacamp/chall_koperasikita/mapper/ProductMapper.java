package com.enigmacamp.chall_koperasikita.mapper;

import com.enigmacamp.chall_koperasikita.dto.response.ProductResponse;
import com.enigmacamp.chall_koperasikita.model.Product;

import java.util.List;

public class ProductMapper {
    // mapper untuk create
    public static ProductResponse convertToProductResponse(Product product){
        // 1. instansiate
        ProductResponse productResponse = new ProductResponse();

        // 2. set value
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setCategory(product.getCategory());
        productResponse.setStock(product.getStock());
        productResponse.setAvailable(product.getIsAvailable());
        productResponse.setCreatedAt(String.valueOf(product.getCreatedAt()));
        productResponse.setUpdatedAt(String.valueOf(product.getUpdatedAt()));
        productResponse.setCreatedBy(product.getCreatedBy());
        productResponse.setUpdatedBy(product.getUpdatedBy());
        productResponse.setDeletedAt(String.valueOf(product.getDeletedAt()));
        productResponse.setDeletedBy(product.getDeletedBy());
        return productResponse;
    }

    // maper untuk list
    public static List<ProductResponse> convertToProductListResponse(List<Product> products) {
        return products.stream()
                .map(ProductMapper::convertToProductResponse)
                .toList();
    }
}
