package ru.kashtanov.product_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.product_service.dto.ProductDto;
import ru.kashtanov.product_service.dto.ProductSaveDto;
import ru.kashtanov.product_service.exception.ProductNotFoundException;
import ru.kashtanov.product_service.exception.ProductNotSavedException;
import ru.kashtanov.product_service.model.Product;
import ru.kashtanov.product_service.repo.ProductRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<ProductDto> findAllProducts() {
        return productRepo.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<Product> findSpecifiedProducts(List<Long> productIds) {
        return productRepo.findAllByIdIn(productIds);
    }

    public List<ProductDto> findProductsBySpecifiedId(String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
        List<Product> specifiedProducts = findSpecifiedProducts(idList);
        List<ProductDto> list = specifiedProducts.stream().map(this::convertToDto).toList();
        return list;
    }

    public Optional<ProductDto> findProductById(Long id) {
        Optional<Product> foundProduct = productRepo.findById(id);
        return foundProduct.map(this::convertToDto);
    }

    public ProductDto createProduct(ProductSaveDto dto) {
        var product = new Product();
        product.setName(dto.getName());
        try {
            productRepo.save(product);
            return convertToDto(product);
        } catch (Exception e) {
            throw new ProductNotSavedException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ProductDto deleteProductById(Long id) {
        Product productNotFound = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found", HttpStatus.NOT_FOUND));
        productRepo.delete(productNotFound);
        return convertToDto(productNotFound);
    }

    private ProductDto convertToDto(Product product) {
        var dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        return dto;
    }
}
