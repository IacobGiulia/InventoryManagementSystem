package com.example.inventorymanagementsystem.repository;

import com.example.inventorymanagementsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
