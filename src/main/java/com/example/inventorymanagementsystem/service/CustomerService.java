package com.example.inventorymanagementsystem.service;

import com.example.inventorymanagementsystem.model.Customer;
import com.example.inventorymanagementsystem.model.Order;
import com.example.inventorymanagementsystem.model.Product;
import com.example.inventorymanagementsystem.repository.CustomerRepository;
import com.example.inventorymanagementsystem.repository.ProductRepository;
import com.example.inventorymanagementsystem.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public CustomerService(CustomerRepository customerRepo, ProductRepository productRepo, OrderRepository orderRepo) {
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public List<Product> viewAllProducts() {
        return productRepo.findAll();
    }

    public Order orderProduct(Long customerId, Long productId, int quantity) {
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
        order.setOrderProductQuantity(quantity);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("PENDING");
        order.setOrderTotalAmount(product.getProductPrice() * quantity);

        return orderRepo.save(order);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer customer = customerRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Customer not found")
        );

        if (updatedCustomer.getCustName() != null) {
            customer.setCustName(updatedCustomer.getCustName());
        }
        if (updatedCustomer.getCustContact() != null) {
            customer.setCustContact(updatedCustomer.getCustContact());
        }
        if (updatedCustomer.getCustAddress() != null) {
            customer.setCustAddress(updatedCustomer.getCustAddress());
        }

        return customerRepo.save(customer);
    }

    public List<Order> viewAllOrders(Long customerId) {
        return orderRepo.findAll().stream()
                .filter(o -> o.getCustomer().getCustId().equals(customerId))
                .toList();
    }
}
