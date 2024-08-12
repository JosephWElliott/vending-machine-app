package com.techelevator;

public class Transactions {
    private double currentMoneyProvided = 0.0;
    private final TransactionLogger logger;

    public Transactions(TransactionLogger logger) {
        this.logger = logger;
    }

    public double getCurrentMoneyProvided() {
        return currentMoneyProvided;
    }

    public void feedMoney(double amount) {
        if (amount > 0 && amount == Math.floor(amount)) {
            currentMoneyProvided += amount;
            logger.log("FEED MONEY ", amount, currentMoneyProvided);
        } else {
            System.out.println("Invalid amount. Please enter a whole dollar amount.");
        }
    }

    public boolean spendMoney(double amount) {
        if (amount > 0 && currentMoneyProvided >= amount) {
            currentMoneyProvided -= amount;
            log("SPEND MONEY", -amount);
            return true;
        } else {
            return false; // Indicate failure
        }
    }

    public String finishTransaction() {
        int quarters = (int) (currentMoneyProvided / 0.25);
        int dimes = (int) ((currentMoneyProvided % 0.25) / 0.10);
        int nickels = (int) (((currentMoneyProvided % 0.25) % 0.10) / 0.05);
        String result = String.format("Change returned: %d quarters, %d dimes, %d nickels", quarters, dimes, nickels);
        log("GIVE CHANGE", currentMoneyProvided);
        currentMoneyProvided = 0.0;
        return result;
    }

    private void log(String action, double amount) {
        logger.log(action, amount, currentMoneyProvided);
    }
}