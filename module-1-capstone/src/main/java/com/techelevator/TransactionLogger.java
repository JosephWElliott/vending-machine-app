package com.techelevator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionLogger {
    private final String logFilePath;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

    public TransactionLogger(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public TransactionLogger() {
        this.logFilePath = "Log.txt";
    }

    public void log(String action, double amount, double balance) {
        try (FileWriter writer = new FileWriter(logFilePath, true)) { // Open FileWriter in append mode
            String logEntry = String.format("%s %s: $%.2f $%.2f%n", dateFormat.format(new Date()), action, amount, balance);
            writer.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}