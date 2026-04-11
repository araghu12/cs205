package edu.monmouth.hw3;

import edu.monmouth.library.*;

import java.io.*;
import java.util.*;

public class HW3 {

    private List<String> stringList;
    private List<LibraryItem> libraryList;

    public static void main(String[] args) {
        HW3 hw3 = new HW3();
        if (!hw3.init(args)) return;
        hw3.showStringList();
        hw3.showLibraryList();
    }

    
    private boolean init(String[] args) {
        if (args.length != LibraryItemConstants.MIN_ARGS) {
            System.err.println("Error: exactly 3 command line arguments required.");
            System.err.println("Usage: HW3 outputFile stringFile libraryFile");
            return false;
        }

      
        try {
            PrintStream ps = new PrintStream(new FileOutputStream(args[0]));
            System.setOut(ps);
            System.setErr(ps);
        } catch (FileNotFoundException e) {
            System.err.println("Is not able to open output file: " + args[0] + " - " + e.getMessage());
            return false;
        }

       
        stringList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringList.add(line);
            }
            System.out.println("String file loaded. Lines read: " + stringList.size());
        } catch (IOException e) {
            System.err.println("Error reading string file: " + e.getMessage());
            return false;
        }

      
        libraryList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(args[2]))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split(",");
                try {
                    if (fields[0].equals(LibraryItemConstants.BOOK_TYPE)
                            && fields.length == LibraryItemConstants.BOOK_FIELD_COUNT) {
                        String title  = fields[1];
                        String author = fields[2];
                        int pages     = Integer.parseInt(fields[3].trim());
                        StatusType status = StatusType.valueOf(fields[4].trim());
                        libraryList.add(new Book(title, author, pages, status));
                    } else if (fields[0].equals(LibraryItemConstants.JOURNAL_TYPE)
                            && fields.length == LibraryItemConstants.JOURNAL_FIELD_COUNT) {
                        String title  = fields[1];
                        String editor = fields[2];
                        int volume    = Integer.parseInt(fields[3].trim());
                        StatusType status = StatusType.valueOf(fields[4].trim());
                        libraryList.add(new Journal(title, editor, volume, status));
                    } else {
                        System.err.println("Invalid line -- wrong type or field count: " + line);
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
            System.out.println("Library items loaded. Items added: " + libraryList.size());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }

        return true;
    }

    
    private void showStringList() {
        System.out.println("\n************ STRING LIST DEMONSTRATIONS ************");

       
        System.out.println(" a. isEmpty ");
        System.out.println("stringList.isEmpty(): " + stringList.isEmpty());

        System.out.println(" c. size ");
        System.out.println("stringList.size(): " + stringList.size());

        
        System.out.println(" b. remove(index 0) ");
        String removes = stringList.remove(0);
        System.out.println("Removed: " + removes);
        System.out.println("List after remove:");
        for (String s : stringList) System.out.println("  " + s);

        
        System.out.println(" d. add ");
        stringList.add("Java Programming");
        System.out.println("Added: \"Java Programming\"");
        System.out.println("List after add:");
        for (String s : stringList) System.out.println("  " + s);

       
        System.out.println(" e. iterator ");
        Iterator<String> it = stringList.iterator();
        while (it.hasNext()) System.out.println("  " + it.next());

       
        System.out.println("f. listIterator forward ");
        ListIterator<String> lit = stringList.listIterator();
        while (lit.hasNext()) System.out.println("  " + lit.next());

      
        System.out.println("f. listIterator reverse ");
        while (lit.hasPrevious()) System.out.println("  " + lit.previous());
    }

    
    private void showLibraryList() {
        System.out.println("\n************ LIBRARY ITEM LIST DEMONSTRATIONS ************");

        
        System.out.println("a. isEmpty ");
        System.out.println("libraryList.isEmpty(): " + libraryList.isEmpty());

       
        System.out.println("c. size ");
        System.out.println("libraryList.size(): " + libraryList.size());

       
        System.out.println(" b. remove(index 0) ");
        LibraryItem removedItem = libraryList.remove(0);
        System.out.println("Removed: " + removedItem);
        System.out.println("List after remove:");
        for (LibraryItem item : libraryList) System.out.println("  " + item);

       
        System.out.println(" d. add ");
        try {
            Book newB = new Book("The Pragmatic Programmer", "Andrew Hunt", 352, StatusType.ONSHELF);
            libraryList.add(newB);
            System.out.println("Added: " + newB);
        } catch (BookException e) {
            System.err.println("BookException adding new book: " + e.getMessage());
        }
        System.out.println("List after add:");
        for (LibraryItem item : libraryList) System.out.println("  " + item);

        
        System.out.println(" e. iterator ");
        Iterator<LibraryItem> it = libraryList.iterator();
        while (it.hasNext()) System.out.println("  " + it.next());

        
        System.out.println("f. listIterator forward");
        ListIterator<LibraryItem> lit = libraryList.listIterator();
        while (lit.hasNext()) System.out.println("  " + lit.next());

       
        System.out.println(" f. listIterator reverse ");
        while (lit.hasPrevious()) System.out.println("  " + lit.previous());

        
        System.out.println(" g. contains (Book IN list: 'The Pragmatic Programmer' by Andrew Hunt)");
        try {
            Book bookInList = new Book("The Pragmatic Programmer", "Andrew Hunt", 1, StatusType.ONSHELF);
            System.out.println("contains result: " + libraryList.contains(bookInList));
        } catch (BookException e) {
            System.err.println("BookException: " + e.getMessage());
        }

        
        System.out.println(" g. contains (Book NOT in list: 'The Great Gatsby' by F. Scott Fitzgerald) ");
        try {
            Book bookNotInList = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1, StatusType.ONSHELF);
            System.out.println("contains result: " + libraryList.contains(bookNotInList));
        } catch (BookException e) {
            System.err.println("BookException: " + e.getMessage());
        }

       
        System.out.println(" h. remove(Journal IN list: 'Science', vol 1) ");
        try {
            Journal journalInList = new Journal("Science",
                    "American Association for the Advancement of Science", 1, StatusType.ONSHELF);
            boolean result = libraryList.remove(journalInList);
            System.out.println("remove result: " + result);
            System.out.println("List after remove:");
            for (LibraryItem item : libraryList) System.out.println("  " + item);
        } catch (JournalException e) {
            System.err.println("JournalException: " + e.getMessage());
        }

       
        System.out.println(" h. remove(Journal NOT in list: 'Nature', vol 1) ");
        try {
            Journal journalNotInList = new Journal("Nature", "Nature Publishing Group", 1, StatusType.ONSHELF);
            boolean res = libraryList.remove(journalNotInList);
            System.out.println("remove result: " + res);
            System.out.println("List after remove:");
            for (LibraryItem item : libraryList) System.out.println("  " + item);
        } catch (JournalException e) {
            System.err.println("JournalException: " + e.getMessage());
        }
    }
}
