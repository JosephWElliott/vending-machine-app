package com.techelevator;

import java.util.Scanner;

public class Application {
	public static void main(String[] args) {
		InventoryManager inventoryManager = new InventoryManager();
		TransactionLogger logger = new TransactionLogger();
		SalesReportGenerator reportGenerator = new SalesReportGenerator();
		Scanner scanner = new Scanner(System.in);
		String inventoryFilePath = "vendingmachine.csv";

		VendingMachine vendingMachine = new VendingMachine(inventoryManager, logger);
		vendingMachine.start(inventoryFilePath);

		VendingMachineConsole console = new VendingMachineConsole(vendingMachine, scanner);
		console.displayMainMenu();
	}
}