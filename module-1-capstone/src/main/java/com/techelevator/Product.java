package com.techelevator;

public abstract class Product {
    private final String slot;
    private final String name;
    private final double price;
    private int quantity;

    public Product(String slot, String name, double price, int quantity) {
        this.slot = slot;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public abstract String getMessage();
}