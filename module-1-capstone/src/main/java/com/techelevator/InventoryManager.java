package com.techelevator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
    // Map to store the inventory of products, with the slot identifier as the key and a list of products as the value
    private final Map<String, List<Product>> inventory = new LinkedHashMap<>();

    // Method to read the inventory from a file
    public void readInventory(String filePath) {
        // Check if the file path is null and throw an exception if it is
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        // Try-with-resources to ensure the BufferedReader is closed after use
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Split the line by the pipe delimiter to extract product details
                String[] parts = line.split("\\|");
                String slot = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                String type = parts[3];
                // Create a product object based on the type
                Product product = switch (type) {
                    case "Chip" -> new Chip(slot, name, price, 5);
                    case "Candy" -> new Candy(slot, name, price, 5);
                    case "Drink" -> new Drink(slot, name, price, 5);
                    case "Gum" -> new Gum(slot, name, price, 5);
                    default -> throw new IllegalArgumentException("Invalid product type: " + type);
                };

                // Add the product to the inventory map, creating a new list if necessary
                inventory.computeIfAbsent(slot, k -> new ArrayList<>()).add(product);
            }
        } catch (IOException | NumberFormatException e) {
            // Handle any I/O exceptions that occur during file reading
            System.err.println("Error reading inventory file: " + e.getMessage());
        }
    }

    // Method to restock a specific slot with a given quantity
    public void restock(String slot, int quantity) {
        // Check if the slot is null and throw an exception if it is
        if (slot == null) {
            throw new NullPointerException("Slot cannot be null");
        }
        // Check if the quantity is negative and throw an exception if it is
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        // Get the list of products for the given slot
        List<Product> products = inventory.get(slot);
        if (products != null) {
            // Update the quantity of each product in the list
            for (Product product : products) {
                product.setQuantity(product.getQuantity() + quantity);
            }
        } else {
            // Throw an exception if the slot does not exist in the inventory
            throw new IllegalArgumentException("Invalid slot: " + slot);
        }
    }

    // Method to get the entire inventory map
    public Map<String, List<Product>> getInventory() {
        return inventory;
    }

    // Method to display the inventory as a formatted string
    public String displayInventory() {
        StringBuilder stringBuilder = new StringBuilder();
        // Iterate over each entry in the inventory map
        for (Map.Entry<String, List<Product>> entry : inventory.entrySet()) {
            // Iterate over each product in the list and append its details to the string builder
            for (Product product : entry.getValue()) {
                stringBuilder.append(product.getSlot()).append(" | ").append(product.getName())
                        .append(" | $").append(product.getPrice()).append(" | Quantity: ").append(product.getQuantity()).append("\n");
            }
        }
        // Return the constructed string
        return stringBuilder.toString();
    }

    // Method to get a product by its slot code
    public Product getProduct(String code) {
        List<Product> products = inventory.get(code);
        if (products == null || products.isEmpty()) {
            return null;
        }
        // Return the first product from the list
        return products.getFirst();
    }
}