package com.techelevator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class SalesReportGenerator {
    private boolean isTestMode;
    private boolean isGenerationEnabled;
    private static final String TEST_FILE_NAME = "SalesReport_test.txt";

    public SalesReportGenerator() {
        this.isTestMode = false; // Default is not test mode
        this.isGenerationEnabled = false; // Default is not to generate reports
    }

    // Method to set test mode
    public void setTestMode(boolean isTestMode) {
        this.isTestMode = isTestMode;
    }

    // Method to enable or disable report generation
    public void setGenerationEnabled(boolean isGenerationEnabled) {
        this.isGenerationEnabled = isGenerationEnabled;
    }

    public void generateSalesReport(Map<String, Product> inventory) {
        if (!isGenerationEnabled) {
            return; // Do not generate report if generation is not enabled
        }

        if (inventory == null) {
            throw new IllegalArgumentException("Inventory cannot be null");
        }

        // Determine the file name based on whether it is in test mode
        String fileName;
        if (isTestMode) {
            fileName = TEST_FILE_NAME;
        } else {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            fileName = "SalesReport_" + timestamp + ".txt";
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            double totalSales = 0;

            // Write the sales data for each product
            for (Map.Entry<String, Product> entry : inventory.entrySet()) {
                Product product = entry.getValue();
                int sold = 5 - product.getQuantity();
                writer.write(product.getName() + "|" + sold + "\n");
                totalSales += sold * product.getPrice();
            }

            // Only write the total sales line if there were sales
            if (totalSales > 0 || !inventory.isEmpty()) {
                writer.write("\n**TOTAL SALES** $" + String.format("%.2f", totalSales) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}