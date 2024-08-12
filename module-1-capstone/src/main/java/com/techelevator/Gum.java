package com.techelevator;

public class Gum extends Product {
    // Constructor
    public Gum(String slot, String name, double price, int quantity) {
        super(slot, name, price, quantity);
    }

    // Methods
    @Override
    public String getMessage() {
        return "Chew Chew, Yum!";
    }
}