package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.model.Customer;
import com.example.inventorymanagementsystem.model.Order;
import com.example.inventorymanagementsystem.model.Product;
import com.example.inventorymanagementsystem.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/products")
    public List<Product> viewAllProducts() {
        return customerService.viewAllProducts();
    }

    @PostMapping("/{customerId}/order/{productId}/{quantity}")
    public Order orderProduct(@PathVariable Long customerId,
                              @PathVariable Long productId,
                              @PathVariable int quantity) {
        return customerService.orderProduct(customerId, productId, quantity);
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(id, updatedCustomer);
    }

    @GetMapping("/{customerId}/orders")
    public List<Order> viewAllOrders(@PathVariable Long customerId) {
        return customerService.viewAllOrders(customerId);
    }
}
