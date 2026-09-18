package com.enigmacamp.chall_koperasikita.service.impl;

import com.enigmacamp.chall_koperasikita.dto.request.SearchProductRequest;
import com.enigmacamp.chall_koperasikita.dto.response.ProductResponse;
import com.enigmacamp.chall_koperasikita.mapper.ProductMapper;
import com.enigmacamp.chall_koperasikita.model.Product;
import com.enigmacamp.chall_koperasikita.repository.ProductRepository;
import com.enigmacamp.chall_koperasikita.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductResponse saveProduct(Product product) {
        validation(product);
        Product newProduct = productRepository.save(product);
        return ProductMapper.convertToProductResponse(newProduct);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product dengan ID " + id
                                        + " tidak ditemukan"
                        )
                );
    }

    @Override
    public List<ProductResponse> searchProducts(SearchProductRequest request) {
        return List.of();
    }

    public void validation(Product product){
        if (product.getName()==null || product.getName().isBlank()){
            throw new NullPointerException("Nama Tidak Boleh Kosong!");
        }
        if (product.getDescription() == null || product.getDescription().isBlank()){
            throw new NullPointerException("Description Tidak Boleh Kosong!");
        }
        if (product.getPrice()==null){
            throw new NullPointerException("Price Tidak Boleh Kosong!");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Price tidak boleh negatif");
        }
        if (product.getCategory() == null || product.getCategory().isBlank()){
            throw new NullPointerException("Category Tidak Boleh Kosong!");
        }
        if (product.getStock()==null){
            throw new NullPointerException("Stock tidak boleh kosong!");
        }
        if (product.getStock()< 0){
            throw new IllegalArgumentException("Stock tidak boleh negatif!");
        }
        if (product.getIsAvailable()==null){
            throw new NullPointerException("IsAvailable tidak boleh kosong!");
        }
    }
}
