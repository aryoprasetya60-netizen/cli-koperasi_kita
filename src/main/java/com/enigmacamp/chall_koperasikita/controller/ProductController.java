package com.enigmacamp.chall_koperasikita.controller;

import com.enigmacamp.chall_koperasikita.dto.request.ProductRequest;
import com.enigmacamp.chall_koperasikita.dto.response.CommonResponse;
import com.enigmacamp.chall_koperasikita.dto.response.ProductResponse;
import com.enigmacamp.chall_koperasikita.mapper.ProductMapper;
import com.enigmacamp.chall_koperasikita.model.Product;
import com.enigmacamp.chall_koperasikita.service.ProductService;
import com.enigmacamp.chall_koperasikita.utils.ResponseUtil;
import com.enigmacamp.chall_koperasikita.utils.constants.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> createProduct(@RequestBody ProductRequest request){
        Product newProduct = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .stock(request.getStock())
                .isAvailable(request.isAvailable())
                .build();

        ProductResponse productResponse =
                productService.saveProduct(newProduct);

        return ResponseUtil.buildResponse(
                HttpStatus.CREATED,
                ResponseMessage.SUCCES_CREATE_DATA,
                productResponse
        );
    }

    @GetMapping("/{id}")
    public ProductResponse getById(
            @PathVariable Long id) {

        Product product = productService.getById(id);

        return ProductMapper.convertToProductResponse(product);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request
    ) {
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());
        product.setIsAvailable(request.isAvailable());

        ProductResponse response =
                productService.updateProduct(id, product);

        return ResponseEntity.ok(response);
    }
}
