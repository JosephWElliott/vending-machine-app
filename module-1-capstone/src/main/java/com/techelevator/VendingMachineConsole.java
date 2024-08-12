package com.techelevator;

import java.util.Scanner;

public class VendingMachineConsole {
    private final VendingMachine vendingMachine;
    private final Scanner scanner;

    public VendingMachineConsole(VendingMachine vendingMachine, Scanner scanner) {
        this.vendingMachine = vendingMachine;
        this.scanner = scanner;
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("(1) Display Vending Machine Items");
            System.out.println("(2) Purchase");
            System.out.println("(3) Exit");
            // Hidden option not displayed
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> vendingMachine.displayItems();
                case "2" -> purchaseMenu();
                case "3" -> System.exit(0);
                case "4" -> vendingMachine.generateSalesReport();
                default -> System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }

    private void purchaseMenu() {
        while (true) {
            System.out.println("Purchase Menu:");
            System.out.println("Current Money Provided: $" + vendingMachine.getCurrentMoneyProvided());
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.println("Enter amount to feed (whole dollars only):");
                    try {
                        double amount = Double.parseDouble(scanner.nextLine());
                        vendingMachine.feedMoney(amount);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }
                case "2" -> {
                    vendingMachine.displayItems(); // Display items here
                    System.out.println("Enter product code:");
                    String code = scanner.nextLine().toUpperCase();
                    try {
                        System.out.println(vendingMachine.selectProduct(code));
                    } catch (VendingMachine.ProductSoldOutException | NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "3" -> {
                    System.out.println(vendingMachine.finishTransaction());
                    System.out.println("Current Money Provided: $0.00");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }
}