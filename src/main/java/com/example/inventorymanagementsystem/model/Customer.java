package com.example.inventorymanagementsystem.model;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int custId;

    private String custName;
    private String custContact;
    private String custAddress;

    public int getCustId() { return custId; }
    public void setCustId(int custId) { this.custId = custId; }

    public String getCustName() { return custName; }
    public void setCustName(String custName) { this.custName = custName; }

    public String getCustContact() { return custContact; }
    public void setCustContact(String custContact) { this.custContact = custContact; }

    public String getCustAddress() { return custAddress; }
    public void setCustAddress(String custAddress) { this.custAddress = custAddress; }
}
