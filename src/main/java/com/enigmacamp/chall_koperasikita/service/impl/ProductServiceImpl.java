package com.enigmacamp.chall_koperasikita.service.impl;

import com.enigmacamp.chall_koperasikita.dto.request.SearchProductRequest;
import com.enigmacamp.chall_koperasikita.dto.response.ProductResponse;
import com.enigmacamp.chall_koperasikita.mapper.ProductMapper;
import com.enigmacamp.chall_koperasikita.model.Product;
import com.enigmacamp.chall_koperasikita.repository.ProductRepository;
import com.enigmacamp.chall_koperasikita.service.ProductService;
import com.enigmacamp.chall_koperasikita.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        product.setCreatedBy("system");
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
    @Transactional
    public ProductResponse updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product tidak ditemukan")
                );

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setStock(product.getStock());
        existingProduct.setIsAvailable(product.getIsAvailable());
        existingProduct.setUpdatedBy(product.getCreatedBy());
        validation(existingProduct);

        Product updatedProduct = productRepository.save(existingProduct);

        return ProductMapper.convertToProductResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product tidak ditemukan")
                );
        productRepository.delete(product);
    }

    @Override
    public Page<ProductResponse> searchProductWithPagination(SearchProductRequest request) {
        // 1. page, size, sortBy, dan direction dalam default values
        // untuk mengantisipasi kalau saat mengirimkan request tidak disertakan valuenya
        int page = (request.getPage() == null || request.getPage() <= 0) ? 0 : request.getPage()-1;
        int size = (request.getSize() == null || request.getSize() <= 0) ? 10 : request.getSize();
        String sortBy = (request.getSortBy() == null || request.getSortBy().isEmpty()) ? "name" : request.getSortBy();
        String direction = (request.getDirection() == null || request.getDirection().isEmpty()) ? "asc" : request.getDirection();

        // 2. get specification
        Specification<Product> specification = ProductSpecification.getSpecification(request);

        // 3. bikin pageRequest
        // a. bikin sort
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        // b. bikin pageRequest
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAll(specification, pageRequest);

        List<ProductResponse> userResponses = ProductMapper.convertToProductListResponse(productPage.getContent());

        return new PageImpl<>(userResponses, pageRequest, productPage.getTotalElements());
    }


    public void validation(Product product){
        if (product.getName()==null || product.getName().isBlank()){
            throw new IllegalArgumentException("Nama Tidak Boleh Kosong!");
        }
        if (product.getDescription() == null || product.getDescription().isBlank()){
            throw new IllegalArgumentException("Description Tidak Boleh Kosong!");
        }
        if (product.getPrice()==null){
            throw new IllegalArgumentException("Price Tidak Boleh Kosong!");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Price tidak boleh negatif");
        }
        if (product.getCategory() == null || product.getCategory().isBlank()){
            throw new IllegalArgumentException("Category Tidak Boleh Kosong!");
        }
        if (product.getStock()==null){
            throw new IllegalArgumentException("Stock tidak boleh kosong!");
        }
        if (product.getStock()< 0){
            throw new IllegalArgumentException("Stock tidak boleh negatif!");
        }
        if (product.getIsAvailable()==null){
            product.setIsAvailable(false);
        }
        if (product.getCreatedBy()==null || product.getCreatedBy().isBlank()){
            product.setCreatedBy("system");
        }
        if (product.getUpdatedBy()==null || product.getUpdatedBy().isBlank()){
            product.setUpdatedBy("system");
        }
    }
}
