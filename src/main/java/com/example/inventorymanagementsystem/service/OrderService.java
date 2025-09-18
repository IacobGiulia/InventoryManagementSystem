package com.example.inventorymanagementsystem.service;

import com.example.inventorymanagementsystem.model.Order;
import com.example.inventorymanagementsystem.model.Customer;
import com.example.inventorymanagementsystem.model.Product;
import com.example.inventorymanagementsystem.repository.OrderRepository;
import com.example.inventorymanagementsystem.repository.CustomerRepository;
import com.example.inventorymanagementsystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderRepository orderRepo,
                        CustomerRepository customerRepo,
                        ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    public Order generateOrder(Long customerId, Long productId, int quantity) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(
                () -> new RuntimeException("Customer not found")
        );
        Product product = productRepo.findById(productId).orElseThrow(
                () -> new RuntimeException("Product not found")
        );

        if (product.getProductQuantityInStock() < quantity) {
            throw new RuntimeException("Not enough stock!");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setOrderDate(LocalDate.now());
        order.setOrderProductQuantity(quantity);
        order.setOrderTotalAmount(product.getProductPrice() * quantity);
        order.setOrderStatus("PENDING");

        return orderRepo.save(order);
    }

    public Order updateOrder(Long orderId, String newStatus) {
        Order order = orderRepo.findById(orderId).orElseThrow(
                () -> new RuntimeException("Order not found")
        );
        order.setOrderStatus(newStatus);
        return orderRepo.save(order);
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepo.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepo.deleteById(orderId);
    }
}
