package com.example.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class BankAccount {
    @Id
    private Long id;
    private Double balance;

    // Standard getters and setters
}