package ru.kashtanov.product_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.product_service.dto.ProductDto;
import ru.kashtanov.product_service.dto.ProductSaveDto;
import ru.kashtanov.product_service.model.Product;
import ru.kashtanov.product_service.repo.ProductRepo;
import ru.kashtanov.product_service.service.ProductService;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductRepo productRepo;

    public ProductController(ProductService productService, ProductRepo productRepo) {
        this.productService = productService;
        this.productRepo = productRepo;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductSaveDto dto) {
        ProductDto productDto = productService.createProduct(dto);
        URI uri = URI.create("/api/v1/products/" + productDto.getId());
        return ResponseEntity.created(uri).body(productDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> takeProductById(@PathVariable("id") Long id) {
        Optional<ProductDto> productById = productService.findProductById(id);
        return productById
                .map(body -> ResponseEntity.ok(body))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public List<ProductDto> takeAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/pointed")
    public List<ProductDto> takeSpecifiedProducts(@RequestParam(required = false,name = "ids") String ids) {
        List<ProductDto> productsBySpecifiedId = productService.findProductsBySpecifiedId(ids);
        System.out.println(productsBySpecifiedId);
        return productService.findProductsBySpecifiedId(ids);
    }

    @DeleteMapping
    public ResponseEntity<ProductDto> deleteProduct(@RequestBody ProductDto dto) {
        productService.deleteProductById(dto.getId());
        return ResponseEntity.noContent().build();
    }


}
