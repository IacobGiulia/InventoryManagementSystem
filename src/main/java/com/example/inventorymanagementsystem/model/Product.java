package com.example.inventorymanagementsystem.model;

import jakarta.persistence.*;

@Entity

public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private double productPrice;
    private int productQuantityInStock;

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public int getProductQuantityInStock() { return productQuantityInStock; }
    public void setProductQuantityInStock(int productQuantityInStock) { this.productQuantityInStock = productQuantityInStock; }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + productId +
                ", name='" + productName + '\'' +
                ", price=" + productPrice +
                ", quantity=" + productQuantityInStock +
                '}';
    }
}