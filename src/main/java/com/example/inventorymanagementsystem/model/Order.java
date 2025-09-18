package com.example.inventorymanagementsystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private double orderTotalAmount;
    private LocalDate orderDate;
    private String orderStatus;
    private int orderProductQuantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cust_id")
    private Customer customer;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public double getOrderTotalAmount() { return orderTotalAmount; }
    public void setOrderTotalAmount(double orderTotalAmount) { this.orderTotalAmount = orderTotalAmount; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public int getOrderProductQuantity() { return orderProductQuantity; }
    public void setOrderProductQuantity(int orderProductQuantity) { this.orderProductQuantity = orderProductQuantity; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}
