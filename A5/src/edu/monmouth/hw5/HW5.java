package edu.monmouth.hw5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Stack;

public class HW5 {

    public static void main(String[] args) {

        
        if (args.length != 1) {
            System.err.println("Error: exactly 1 command line argument required (path to properties file).");
            System.exit(1);
        }

        Properties p = init(args[0]);
        if (p == null) {
            System.exit(1);
        }

       
        p.list(System.out);

       
        checkWordPalindromes(p);

        
        checkNumberPalindromes(p);
    }

  

    private static Properties init(String propertiesFile) {

        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            props.load(fis);
        } catch (FileNotFoundException e) {
            System.err.println("Error: properties file not found: " + propertiesFile);
            return null;
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            return null;
        }

       
        String logFileName = props.getProperty("log_file_name");
        if (logFileName == null) {
            System.err.println("Error: 'log_file_name' property was not found in properties file.");
            return null;
        }

        try {
            PrintStream logStream = new PrintStream(logFileName);
            System.setOut(logStream);
            System.setErr(logStream);
        } catch (FileNotFoundException e) {
            System.err.println("Error: could not open this file: " + logFileName);
            return null;
        }

        return props;
    }

    

    private static void checkWordPalindromes(Properties props) {

        String wordsValue = props.getProperty("words");
        if (wordsValue == null) {
            System.err.println("Error: 'words' property not found in properties file.");
            return;
        }

        String[] words = wordsValue.split(",");

        System.out.println("\n**************** Word Palindrome Check ****************");

        for (String word : words) {
            word = word.trim();
            boolean isPalindrome = isWordPalindrome(word);
            if (isPalindrome) {
                System.out.println(word + " is a palindrome");
            } else {
                System.out.println(word + " is NOT a palindrome");
            }
        }
    }

    private static boolean isWordPalindrome(String word) {

       
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < word.length(); i++) {
            stack.push(word.charAt(i));
        }

      
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }

       
        return reversed.toString().equals(word);
    }

    

    private static void checkNumberPalindromes(Properties props) {

        String rangeValue = props.getProperty("number_range");
        if (rangeValue == null) {
            System.err.println("Error: 'number_range' property not found in properties file.");
            return;
        }

        String[] parts = rangeValue.split(",");
        if (parts.length != 2) {
            System.err.println("Error: 'number_range' must contain exactly 2 comma-separated integers.");
            return;
        }

        int b;
        int ed;
        try {
            b = Integer.parseInt(parts[0].trim());
            ed = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            System.err.println("Error: 'number_range' values must be integers. " + e.getMessage());
            return;
        }

        System.out.println("\n**************** Integer Palindrome Check (" + b + " to " + ed + ") ****************");

        StringBuilder output = new StringBuilder();
        for (int i = b; i <= ed; i++) {
            if (isIntPalindrome(i)) {
                if (output.length() > 0) {
                    output.append(" ");
                }
                output.append(i);
            }
        }

        System.out.println(output.toString());
    }

    private static boolean isIntPalindrome(int number) {

        String numStr = Integer.toString(number);

        
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < numStr.length(); i++) {
            stack.push(numStr.charAt(i));
        }

       
        StringBuilder r = new StringBuilder();
        while (!stack.isEmpty()) {
            r.append(stack.pop());
        }

        return r.toString().equals(numStr);
    }
}
