package com.example.inventorymanagementsystem.service;

import com.example.inventorymanagementsystem.model.Product;
import com.example.inventorymanagementsystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
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

        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found with id " + id);
        }
        productRepo.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}
