package com.techelevator;

public class Chip extends Product {
    // Constructor
    public Chip(String slot, String name, double price, int quantity) {
        super(slot, name, price, quantity);
    }

    // Methods
    @Override
    public String getMessage() {
        return "Crunch Crunch, Yum!";
    }
}
