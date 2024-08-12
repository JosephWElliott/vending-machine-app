package com.techelevator;

class Drink extends Product {
    // Constructor
    public Drink(String slot, String name, double price, int quantity) {
        super(slot, name, price, quantity);
    }

    // Methods
    @Override
    public String getMessage() {
        return "Glug Glug, Yum!";
    }
}

