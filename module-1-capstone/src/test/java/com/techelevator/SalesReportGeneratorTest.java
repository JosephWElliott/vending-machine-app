package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SalesReportGeneratorTest {
    private SalesReportGenerator salesReportGenerator;
    private static final String SALES_REPORT_FILE = "SalesReport_test.txt";

    @BeforeEach
    void setUp() {
        salesReportGenerator = new SalesReportGenerator();
        salesReportGenerator.setTestMode(true);             // Enable test mode
        salesReportGenerator.setGenerationEnabled(true);    // Enable report generation for tests

        // Clear the sales report file if it exists
        try {
            Files.deleteIfExists(new File(SALES_REPORT_FILE).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void generateSalesReport_HappyPath() {
        // Prepare test data
        Map<String, Product> inventory = new HashMap<>();
        inventory.put("A1", new Chip("A1", "Potato Crisps", 3.05, 5));
        inventory.put("B1", new Candy("B1", "Moonpie", 1.80, 5));
        inventory.put("C1", new Drink("C1", "Cola", 1.25,  5));

        // Generate sales report
        salesReportGenerator.generateSalesReport(inventory);

        // Check if the file exists and is not empty
        File reportFile = new File(SALES_REPORT_FILE);
        assertTrue(reportFile.exists(), "Sales report file should exist");
        assertTrue(reportFile.length() > 0, "Sales report file should not be empty");
    }

    @Test
    void generateSalesReport_EdgeCase() {
        // Prepare empty test data
        Map<String, Product> inventory = new HashMap<>();

        // Generate sales report
        salesReportGenerator.generateSalesReport(inventory);

        // Check if the file exists and is empty
        File reportFile = new File(SALES_REPORT_FILE);
        assertTrue(reportFile.exists(), "Sales report file should exist");
        assertEquals(0, reportFile.length(), "Sales report file should be empty");
    }

    @Test
    void generateSalesReport_Null() {
        assertThrows(IllegalArgumentException.class, () -> salesReportGenerator.generateSalesReport(null));
    }
}