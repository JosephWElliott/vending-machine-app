package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {
    private VendingMachine vendingMachine;

    @BeforeEach
    void setUp() {
        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.readInventory("src/test/resources/test_vendingmachine.csv");
        TransactionLogger logger = new TransactionLogger();
        vendingMachine = new VendingMachine(inventoryManager, logger);
    }

    @Test
    void feedMoney_HappyPath() {
        vendingMachine.feedMoney(5);
        assertEquals(5, vendingMachine.getCurrentMoneyProvided());
    }

    @Test
    void feedMoney_EdgeCase() {
        assertThrows(IllegalArgumentException.class, () -> vendingMachine.feedMoney(-1));
    }

    @Test
    void feedMoney_Null() {
        assertThrows(IllegalArgumentException.class, () -> vendingMachine.feedMoney(0));
    }

    @Test
    void selectProduct_HappyPath() {
        vendingMachine.feedMoney(5);
        String result = vendingMachine.selectProduct("A1");
        assertTrue(result.contains("Dispensed: Potato Crisps"));
    }

    @Test
    void selectProduct_EdgeCase() {
        vendingMachine.feedMoney(1);
        String result = vendingMachine.selectProduct("A1");
        assertEquals("Insufficient funds. Please feed more money.", result);
    }

    @Test
    void selectProduct_Null() {
        assertThrows(NullPointerException.class, () -> vendingMachine.selectProduct(null));
    }
}