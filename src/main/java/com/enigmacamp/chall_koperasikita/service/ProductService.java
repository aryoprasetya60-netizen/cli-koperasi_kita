package com.enigmacamp.chall_koperasikita.service;

import com.enigmacamp.chall_koperasikita.dto.request.SearchProductRequest;
import com.enigmacamp.chall_koperasikita.dto.response.ProductResponse;
import com.enigmacamp.chall_koperasikita.model.Product;

import java.util.List;

public interface ProductService {
    ProductResponse saveProduct(Product product);
    Product getById(Long id);
    ProductResponse updateProduct(Long id, Product product);
    List<ProductResponse> searchProducts(SearchProductRequest request);

}
