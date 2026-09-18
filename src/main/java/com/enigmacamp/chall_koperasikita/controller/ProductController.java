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
        Product newProduct = new Product(
                request.getName()
                ,request.getDescription()
                ,request.getPrice()
                ,request.getCategory()
                ,request.getStock()
                ,request.isAvailable());
        ProductResponse productResponse =productService.saveProduct(newProduct);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, ResponseMessage.SUCCES_CREATE_DATA, productResponse);
    }

    @GetMapping("/{id}")
    public ProductResponse getById(
            @PathVariable Long id) {

        Product product = productService.getById(id);

        return ProductMapper.convertToProductResponse(product);
    }
}
