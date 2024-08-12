package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {
    private InventoryManager inventoryManager;
    private static final String TEST_FILE = "src/test/resources/test_vendingmachine.csv";
    private static final String EMPTY_FILE = "src/test/resources/test_empty_inventory.csv";
    private static final String INVALID_FILE = "src/test/resources/test_invalid_product_type.csv";

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
        createTestFiles();
    }

    @Test
    void readInventory_HappyPath() {
        inventoryManager.readInventory(TEST_FILE);
        Map<String, List<Product>> inventory = inventoryManager.getInventory();
        assertNotNull(inventory);
        assertFalse(inventory.isEmpty(), "Inventory should not be empty");
    }

    @Test
    void readInventory_EdgeCase() {
        inventoryManager.readInventory(EMPTY_FILE);
        Map<String, List<Product>> inventory = inventoryManager.getInventory();
        assertTrue(inventory.isEmpty(), "Inventory should be empty for an empty file");
    }

    @Test
    void readInventory_Null() {
        assertThrows(IllegalArgumentException.class, () -> inventoryManager.readInventory(null));
    }

    @Test
    void readInventory_InvalidProductType() {
        assertThrows(IllegalArgumentException.class, () -> inventoryManager.readInventory(INVALID_FILE));
    }

    private void createTestFiles() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE))) {
            writer.write("A1|Potato Crisps|3.05|Chip\n");
            writer.write("B1|Moonpie|1.80|Candy\n");
            writer.write("C1|Cola|1.25|Drink\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPTY_FILE))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVALID_FILE))) {
            writer.write("A1|Potato Crisps|3.05|InvalidType\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}