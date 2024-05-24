package com.Design;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilityClass {
	public static List<String> readLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        // Open the file and create a BufferedReader to read it
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line from the file and add it to the list
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        // Return the list of lines read from the file
        return lines;
    }        
}

