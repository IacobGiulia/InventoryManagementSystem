package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.model.Order;
import com.example.inventorymanagementsystem.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{customerId}/{productId}/{quantity}")
    public Order generateOrder(@PathVariable Long customerId,
                               @PathVariable Long productId,
                               @PathVariable int quantity) {
        return orderService.generateOrder(customerId, productId, quantity);
    }

    @PutMapping("/{orderId}/status")
    public Order updateOrder(@PathVariable Long orderId,
                             @RequestParam String status) {
        return orderService.updateOrder(orderId, status);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
