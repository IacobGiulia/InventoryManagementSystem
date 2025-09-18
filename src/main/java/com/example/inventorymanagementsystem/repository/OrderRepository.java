package com.example.inventorymanagementsystem.repository;

import com.example.inventorymanagementsystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
