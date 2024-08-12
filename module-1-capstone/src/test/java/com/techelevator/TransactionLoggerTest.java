package com.techelevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class TransactionLoggerTest {
    private static final String LOG_FILE_NAME = "test_Log.txt";
    private TransactionLogger logger;

    @BeforeEach
    void setUp() {
        logger = new TransactionLogger(LOG_FILE_NAME);
        try (FileWriter writer = new FileWriter(LOG_FILE_NAME)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void log_HappyPath() {
        // Log a transaction with zero values
        logger.log("FEED MONEY", 5.00, 5.00);
        File logFile = new File(LOG_FILE_NAME);
        // Check that the log file is created and not empty
        assertTrue(logFile.exists());
        assertTrue(logFile.length() > 0);

        // Read the content of the log file and verify it contains the expected log entry
        try {
            String content = new String(Files.readAllBytes(Paths.get(LOG_FILE_NAME)));
            assertTrue(content.contains("FEED MONEY"), "Log file should contain 'FEED MONEY'");
            assertTrue(content.contains("$5.00"), "Log file should contain '$5.00'");
        } catch (IOException e) {
            fail("Failed to read log file.");
        }
    }

    @Test
    void log_EdgeCase() {
        // Log a transaction with zero values
        logger.log("FEED MONEY", 0, 0);
        File logFile = new File(LOG_FILE_NAME);
        // Check that the log file is created and not empty
        assertTrue(logFile.exists());
        assertTrue(logFile.length() > 0);

        // Read the content of the log file and verify it contains the expected log entry
        try {
            String content = new String(Files.readAllBytes(Paths.get(LOG_FILE_NAME)));
            assertTrue(content.contains("FEED MONEY"), "Log file should contain 'FEED MONEY'");
            assertTrue(content.contains("$0.00"), "Log file should contain '$0.00'");
        } catch (IOException e) {
            fail("Failed to read log file.");
        }
    }

    @Test
    void log_NullAction() {
        // Log a transaction with a null action
        logger.log(null, 0, 0);
        File logFile = new File(LOG_FILE_NAME);
        // Check that the log file is created and not empty
        assertTrue(logFile.exists());
        assertTrue(logFile.length() > 0);

        // Read the content of the log file and verify it contains the null log entry
        try {
            String content = new String(Files.readAllBytes(Paths.get(LOG_FILE_NAME)));

            // Verify that the null log entry was added
            assertTrue(content.contains("null"), "Log file should contain 'null' action");
        } catch (IOException e) {
            fail("Failed to read log file.");
        }
    }
}