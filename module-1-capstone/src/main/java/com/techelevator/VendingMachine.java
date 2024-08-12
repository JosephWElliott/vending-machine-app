package com.techelevator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    private final InventoryManager inventoryManager;
    private final Transactions transactions;
    private double totalSales = 0.0;
    private final DecimalFormat df = new DecimalFormat("#.00");

    public VendingMachine(InventoryManager inventoryManager, TransactionLogger logger) {
        this.inventoryManager = inventoryManager;
        this.transactions = new Transactions(logger);
    }

    public void start(String inventoryFilePath) {
        inventoryManager.readInventory(inventoryFilePath);
    }

    public void feedMoney(double amount) {
        transactions.feedMoney(amount);
    }

    public String selectProduct(String code) {
        Product product = inventoryManager.getProduct(code);

        if (product == null) {
            return "Invalid product code. Please try again.";
        } else if (product.getQuantity() == 0) {
            return "Product is sold out. Please select another product.";
        } else if (transactions.getCurrentMoneyProvided() < product.getPrice()) {
            return "Insufficient funds. Please feed more money.";
        } else {
            product.setQuantity(product.getQuantity() - 1);
            boolean success = transactions.spendMoney(product.getPrice());
            if (!success) {
                return "Transaction failed. Please try again.";
            }
            totalSales += product.getPrice();
            return String.format("Dispensed: %s - $%.2f\nMoney remaining: $%.2f\n%s",
                    product.getName(), product.getPrice(), transactions.getCurrentMoneyProvided(), product.getMessage());
        }
    }

    public String finishTransaction() {
        return transactions.finishTransaction();
    }

    public static class ProductSoldOutException extends IllegalArgumentException {
        public ProductSoldOutException(String message) {
            super(message);
        }
    }

    public void generateSalesReport() {
        if (totalSales == 0) {
            System.out.println("No sales have been made. Sales report will not be generated.");
            return;
        }

        String fileName = "SalesReport_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Map.Entry<String, List<Product>> entry : inventoryManager.getInventory().entrySet()) {
                for (Product product : entry.getValue()) {
                    int sold = 5 - product.getQuantity();  // Assuming initial quantity is 5
                    writer.write(product.getName() + "|" + sold + "\n");
                }
            }
            writer.write("\n**TOTAL SALES** $" + String.format("%.2f", totalSales) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateSalesReport(StringWriter stringWriter) throws IOException {
        if (totalSales == 0) {
            System.out.println("No sales have been made. Sales report will not be generated.");
            return;
        }
        writeSalesReport(stringWriter);
    }

    private void writeSalesReport(Appendable writer) throws IOException {
        for (Map.Entry<String, List<Product>> entry : inventoryManager.getInventory().entrySet()) {
            for (Product product : entry.getValue()) {
                int sold = 5 - product.getQuantity();  // Assuming initial quantity is 5
                writer.append(product.getName()).append("|").append(String.valueOf(sold)).append("\n");
            }
        }
        writer.append("\n**TOTAL SALES** $").append(String.format("%.2f", totalSales));
    }

    public void displayItems() {
        Map<String, List<Product>> inventory = inventoryManager.getInventory();

        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (Map.Entry<String, List<Product>> entry : inventory.entrySet()) {
            for (Product product : entry.getValue()) {
                System.out.println(product.getSlot() + " | " + product.getName() + " | $" + product.getPrice() + " | Quantity: " + product.getQuantity());
            }
        }
    }

    public double getCurrentMoneyProvided() {
        return transactions.getCurrentMoneyProvided();
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}