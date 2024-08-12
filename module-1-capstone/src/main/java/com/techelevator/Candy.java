package com.techelevator;

public class Candy extends Product {
    // Constructor
    public Candy(String slot, String name, double price, int quantity) {
        super(slot, name, price, quantity);
    }

    // Methods
    @Override
    public String getMessage() {
        return "Munch Munch, Yum!";
    }
}