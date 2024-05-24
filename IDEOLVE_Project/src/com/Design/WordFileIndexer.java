package com.Design;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WordFileIndexer {
	private Map<String, Set<Integer>> index;
	private Set<String> excludeWords;

	public WordFileIndexer() {
	    this.index = new TreeMap<>();
	    this.excludeWords = new HashSet<>();
	}
	
	public void readExcludeWords(String filename) throws IOException {
        List<String> lines = UtilityClass.readLines(filename);
        for (String line : lines) {
            excludeWords.add(line.trim().toLowerCase());
        }
    }
	
	public void indexPage(String filename, int pageNumber) throws IOException {
	    List<String> lines = UtilityClass.readLines(filename);
	    for (String line : lines) {
	        String[] words = line.split("\\W+");
	        for (String word : words) {
	            if (word.isEmpty() || containsDigit(word)) continue;
	            word = word.toLowerCase();
	            if (!excludeWords.contains(word)) {
	                if (!index.containsKey(word)) {
	                    index.put(word, new HashSet<>());
	                }
	                index.get(word).add(pageNumber);
	            }
	        }
	    }
	}

	private boolean containsDigit(String word) {
	    for (char c : word.toCharArray()) {
	        if (Character.isDigit(c)) {
	            return true;
	        }
	    }
	    return false;
	}

	
	public void writeIndex(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
        	writer.println("Word : Page Numbers");
        	writer.println("---------------------");
            for (Map.Entry<String, Set<Integer>> entry : index.entrySet()) {
                writer.print(entry.getKey() + " : ");
                List<Integer> pages = new ArrayList<>(entry.getValue());
                Collections.sort(pages);
                for (int i = 0; i < pages.size(); i++) {
                    if (i > 0) writer.print(",");
                    writer.print(pages.get(i));
                }
                writer.println();
            }
        }
    }


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			WordFileIndexer index = new WordFileIndexer();
			index.readExcludeWords("D:\\IdevolveFiles\\exclude-words.txt");
			index.indexPage("D:\\IdevolveFiles\\Page1.txt", 1);
            index.indexPage("D:\\IdevolveFiles\\Page2.txt", 2);
            index.indexPage("D:\\IdevolveFiles\\Page3.txt", 3);
            index.writeIndex("D:\\IdevolveFiles\\Index.txt");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}


