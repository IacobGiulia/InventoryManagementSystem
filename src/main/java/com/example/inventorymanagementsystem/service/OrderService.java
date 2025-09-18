package com.example.inventorymanagementsystem.service;

import com.example.inventorymanagementsystem.model.Order;
import com.example.inventorymanagementsystem.model.Customer;
import com.example.inventorymanagementsystem.model.Product;
import com.example.inventorymanagementsystem.repository.OrderRepository;
import com.example.inventorymanagementsystem.repository.CustomerRepository;
import com.example.inventorymanagementsystem.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

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

        Order saved = orderRepo.save(new Order(/* populate */));
        logger.info("Order generated: orderId={}, customerId={}, productId={}, quantity={}", saved.getOrderId(), customerId, productId, quantity);
        return saved;
    }

    public Order updateOrder(Long orderId, String newStatus) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(newStatus);
        Order saved = orderRepo.save(order);
        logger.info("Order updated: orderId={}, newStatus={}", orderId, newStatus);
        return saved;
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepo.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepo.deleteById(orderId);
        logger.info("Order deleted: orderId={}", orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
