package com.example.rastuarent;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileManager {

    private static final String FILE = "sales.txt";

    // Save transaction to file
    public static void saveTransaction(String receipt) {
        try (FileWriter writer = new FileWriter(FILE, true)) {
            writer.write("\n==============================\n");
            writer.write("DATE: " + LocalDateTime.now() + "\n");
            writer.write(receipt);
            writer.write("\n==============================\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}