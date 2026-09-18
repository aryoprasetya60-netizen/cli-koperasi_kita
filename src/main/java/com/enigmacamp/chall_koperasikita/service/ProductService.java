package com.enigmacamp.chall_koperasikita.service;

import com.enigmacamp.chall_koperasikita.dto.response.ProductResponse;
import com.enigmacamp.chall_koperasikita.model.Product;

public interface ProductService {
    ProductResponse saveProduct(Product product);
}
