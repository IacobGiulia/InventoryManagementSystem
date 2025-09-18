package com.example.inventorymanagementsystem.service;

import com.example.inventorymanagementsystem.model.Product;
import com.example.inventorymanagementsystem.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepo;
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Product addProduct(Product product) {
        Product saved = productRepo.save(product);
        logger.info("Product added: {}", saved.getProductName());
        return saved;
    }

    public Product updateProduct(long id, Product updatedProduct) {
        Product product = productRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found with id " + id)
        );

        if (updatedProduct.getProductName() != null) {
            product.setProductName(updatedProduct.getProductName());
        }
        if (updatedProduct.getProductPrice() != 0) {
            product.setProductPrice(updatedProduct.getProductPrice());
        }
        if (updatedProduct.getProductQuantityInStock() != 0) {
            product.setProductQuantityInStock(updatedProduct.getProductQuantityInStock());
        }
        Product saved = productRepo.save(product);
        logger.info("Product updated: id={}, newQuantity={}", id, saved.getProductQuantityInStock());
        return saved;
    }

    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found with id " + id);
        }
        productRepo.deleteById(id);
        logger.info("Product deleted: id={}", id);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}
