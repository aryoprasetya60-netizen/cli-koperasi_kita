package com.enigmacamp.chall_koperasikita.controller;

import com.enigmacamp.chall_koperasikita.dto.request.ProductRequest;
import com.enigmacamp.chall_koperasikita.dto.request.SearchProductRequest;
import com.enigmacamp.chall_koperasikita.dto.response.CommonResponse;
import com.enigmacamp.chall_koperasikita.dto.response.ProductResponse;
import com.enigmacamp.chall_koperasikita.mapper.ProductMapper;
import com.enigmacamp.chall_koperasikita.model.Product;
import com.enigmacamp.chall_koperasikita.service.ProductService;
import com.enigmacamp.chall_koperasikita.utils.ResponseUtil;
import com.enigmacamp.chall_koperasikita.utils.constants.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
                .isAvailable(request.getIsAvailable())
                .build();

        ProductResponse productResponse = productService.saveProduct(newProduct);

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
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request
    ) {
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());
        product.setIsAvailable(request.getIsAvailable());

        ProductResponse productResponse = productService.updateProduct(id, product);

        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.SUCCES_UPDATE_DATA,
                productResponse
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteProduct(
            @PathVariable Long id
    ) {
        productService.deleteProductById(id);

        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.SUCCES_DELETE_DATA,
                null
        );
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> searchWithPaging(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction
    ) {
        SearchProductRequest request = SearchProductRequest.builder()
                .name(name)
                .category(category)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .minStock(minStock)
                .isAvailable(isAvailable)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();

        Page<ProductResponse> productResponse = productService.searchProductWithPagination(request);

        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.SUCCES_GET_DATA,
                productResponse.getContent(),
                productResponse);
    }
}
