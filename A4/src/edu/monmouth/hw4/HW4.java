package edu.monmouth.hw4;

import edu.monmouth.library.*;

import java.io.*;
import java.util.*;


 
public class HW4 {

 
    private Set<LibraryItem> hashSet;

    public static void main(String[] args) {
        HW4 hw4 = new HW4();
        if (!hw4.init(args)) return;
        hw4.verifyEqualsAndHashCode();
        hw4.loadAndAddToHashSet(args[1]);
        hw4.iterateWithIterator();
        hw4.testContains();
        hw4.testRemove();
        hw4.iterateWithForEach();
    }

    
    private boolean init(String[] args) {
        if (args.length != HW4Constants.NUM_ARGS) {
            System.err.println("Error: exactly " + HW4Constants.NUM_ARGS + " command line arguments required.");
            System.err.println("Usage: HW4 <outputFile> <libraryItemsFile>");
            return false;
        }

        try {
            PrintStream ps = new PrintStream(new FileOutputStream(args[0]));
            System.setOut(ps);
            System.setErr(ps);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open output file: " + args[0] + " - " + e.getMessage());
            return false;
        }

        hashSet = new HashSet<>();
        return true;
    }

   
    private void verifyEqualsAndHashCode() {
        System.out.println("\n*********** VERIFYING equals() AND hashCode() CONSISTENCY ***********");

        
        try {
            Book b1 = new Book("Clean Code", "Robert Martin", 464, StatusType.ONSHELF);
            Book b2 = new Book("Clean Code", "Robert Martin", 999, StatusType.MISSING); 
            System.out.println("\n-- Equivalent Books (same author + title, different pages/status) --");
            System.out.println("b1: " + b1);
            System.out.println("b2: " + b2);
            System.out.println("b1.equals(b2): " + b1.equals(b2));
            System.out.println("b1.hashCode(): " + b1.hashCode());
            System.out.println("b2.hashCode(): " + b2.hashCode());
            System.out.println("hashCodes match: " + (b1.hashCode() == b2.hashCode()));
        } catch (BookException e) {
            System.err.println("BookException during equivalence test: " + e.getMessage());
        }

        
        try {
            Book b3 = new Book("Clean Code", "Robert Martin", 464, StatusType.ONSHELF);
            Book b4 = new Book("The Pragmatic Programmer", "Andrew Hunt", 352, StatusType.ONSHELF);
            System.out.println("\n-- Inequivalent Books (different author + title) --");
            System.out.println("b3: " + b3);
            System.out.println("b4: " + b4);
            System.out.println("b3.equals(b4): " + b3.equals(b4));
            System.out.println("b3.hashCode(): " + b3.hashCode());
            System.out.println("b4.hashCode(): " + b4.hashCode());
            System.out.println("hashCodes differ: " + (b3.hashCode() != b4.hashCode()));
        } catch (BookException e) {
            System.err.println("BookException during inequivalence test: " + e.getMessage());
        }

       
        try {
            Journal j1 = new Journal("Science", "AAAS", 1, StatusType.ONSHELF);
            Journal j2 = new Journal("Science", "AAAS", 1, StatusType.BORROWED); 
            System.out.println("\n-- Equivalent Journals (same title + editor + volume, different status) --");
            System.out.println("j1: " + j1);
            System.out.println("j2: " + j2);
            System.out.println("j1.equals(j2): " + j1.equals(j2));
            System.out.println("j1.hashCode(): " + j1.hashCode());
            System.out.println("j2.hashCode(): " + j2.hashCode());
            System.out.println("hashCodes match: " + (j1.hashCode() == j2.hashCode()));
        } catch (JournalException e) {
            System.err.println("JournalException during equivalence test: " + e.getMessage());
        }

       
        try {
            Journal j3 = new Journal("Science", "AAAS", 1, StatusType.ONSHELF);
            Journal j4 = new Journal("Nature", "Nature Publishing Group", 5, StatusType.ONSHELF);
            System.out.println("\n-- Inequivalent Journals (different title + editor + volume) --");
            System.out.println("j3: " + j3);
            System.out.println("j4: " + j4);
            System.out.println("j3.equals(j4): " + j3.equals(j4));
            System.out.println("j3.hashCode(): " + j3.hashCode());
            System.out.println("j4.hashCode(): " + j4.hashCode());
            System.out.println("hashCodes differ: " + (j3.hashCode() != j4.hashCode()));
        } catch (JournalException e) {
            System.err.println("JournalException during inequivalence test: " + e.getMessage());
        }
    }

    
    private void loadAndAddToHashSet(String filename) {
        System.out.println("\n*********** LOADING FROM FILE AND ADDING TO HASHSET ***********");

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    System.out.println("Skipping empty line.");
                    continue;
                }

                String[] f = line.split(",");

                try {
                    if (f[0].equals(HW4Constants.BOOK_TYPE)
                            && f.length == HW4Constants.BOOK_FIELD_COUNT) {

                        String title  = f[1];
                        String author = f[2];
                        int pages     = Integer.parseInt(f[3].trim());
                        StatusType status = StatusType.valueOf(f[4].trim());
                        Book book = new Book(title, author, pages, status);
                        boolean added = hashSet.add(book);
                        System.out.println("add(" + book + ") returned: " + added);

                    } else if (f[0].equals(HW4Constants.JOURNAL_TYPE)
                            && f.length == HW4Constants.JOURNAL_FIELD_COUNT) {

                        String title  = f[1];
                        String editor = f[2];
                        int volume    = Integer.parseInt(f[3].trim());
                        StatusType status = StatusType.valueOf(f[4].trim());
                        Journal journal = new Journal(title, editor, volume, status);
                        boolean added = hashSet.add(journal);
                        System.out.println("add(" + journal + ") returned: " + added);

                    } else {
                        System.err.println("Invalid line -- wrong type or field count: [" + line + "]");
                    }

                } catch (BookException e) {
                    System.err.println("BookException on line [" + line + "]: " + e.getMessage());
                } catch (JournalException e) {
                    System.err.println("JournalException on line [" + line + "]: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.err.println("NumberFormatException on line [" + line + "]: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.err.println("IllegalArgumentException on line [" + line + "]: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("IOException reading file: " + e.getMessage());
        }

        System.out.println("\nTotal items in HashSet after loading: " + hashSet.size());
    }

   
    private void iterateWithIterator() {
        System.out.println("\n*********** ITERATING WITH iterator() ***********");
        Iterator<LibraryItem> it = hashSet.iterator();
        while (it.hasNext()) {
            System.out.println("  " + it.next());
        }
    }

    private void testContains() {
        System.out.println("\n*********** Using contains() ***********");

        
        try {
            Book bookIn = new Book("To Kill a Mockingbird", "Harper Lee", 1, StatusType.ONSHELF);
            System.out.println("\ncontains(Book IN set): " + bookIn);
            System.out.println("contains() returned: " + hashSet.contains(bookIn));
        } catch (BookException e) {
            System.err.println("BookException: " + e.getMessage());
        }

        
        try {
            Book bookOut = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1, StatusType.ONSHELF);
            System.out.println("\ncontains(Book NOT in set): " + bookOut);
            System.out.println("contains() returned: " + hashSet.contains(bookOut));
        } catch (BookException e) {
            System.err.println("BookException: " + e.getMessage());
        }

        
        try {
            Journal journalIn = new Journal("Science",
                    "American Association for the Advancement of Science", 1, StatusType.MISSING);
            System.out.println("\ncontains(Journal IN set): " + journalIn);
            System.out.println("contains() returned: " + hashSet.contains(journalIn));
        } catch (JournalException e) {
            System.err.println("JournalException: " + e.getMessage());
        }

        
        try {
            Journal journalOut = new Journal("Nature", "Nature Publishing Group", 1, StatusType.ONSHELF);
            System.out.println("\ncontains(Journal NOT in set): " + journalOut);
            System.out.println("contains() returned: " + hashSet.contains(journalOut));
        } catch (JournalException e) {
            System.err.println("JournalException: " + e.getMessage());
        }
    }

   
    private void testRemove() {
        System.out.println("\n*********** Using remove() ***********");

        
        try {
            Book bookIn = new Book("To Kill a Mockingbird", "Harper Lee", 1, StatusType.ONSHELF);
            System.out.println("\nremove(Book IN set): " + bookIn);
            System.out.println("remove() returned: " + hashSet.remove(bookIn));
        } catch (BookException e) {
            System.err.println("BookException: " + e.getMessage());
        }

        
        try {
            Book bookOut = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1, StatusType.ONSHELF);
            System.out.println("\nremove(Book NOT in set): " + bookOut);
            System.out.println("remove() returned: " + hashSet.remove(bookOut));
        } catch (BookException e) {
            System.err.println("BookException: " + e.getMessage());
        }

       
        try {
            Journal journalIn = new Journal("Science",
                    "American Association for the Advancement of Science", 1, StatusType.BORROWED);
            System.out.println("\nremove(Journal IN set): " + journalIn);
            System.out.println("remove() returned: " + hashSet.remove(journalIn));
        } catch (JournalException e) {
            System.err.println("JournalException: " + e.getMessage());
        }

        
        try {
            Journal journalOut = new Journal("Nature", "Nature Publishing Group", 1, StatusType.ONSHELF);
            System.out.println("\nremove(Journal NOT in set): " + journalOut);
            System.out.println("remove() returned: " + hashSet.remove(journalOut));
        } catch (JournalException e) {
            System.err.println("JournalException: " + e.getMessage());
        }
    }

    
    private void iterateWithForEach() {
        System.out.println("\n***********FINAL ITERATION (for-each) AFTER REMOVES ***********");
        System.out.println("Items remaining in HashSet: " + hashSet.size());
        for (LibraryItem item : hashSet) {
            System.out.println("  " + item);
        }
    }
}
