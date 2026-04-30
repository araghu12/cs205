package edu.monmouth.hw6;

import edu.monmouth.library.*;

import java.io.*;
import java.util.*;

public class HW6 {

    private Set<Book>    bookTreeSet;
    private Set<Journal> journalTreeSet;
    private List<Book>   bookList;
    private List<Journal> journalList;

    public static void main(String[] args) {
        HW6 hw6 = new HW6();
        Properties p = hw6.init(args);
        if (p == null) return;

        String libraryFile = p.getProperty(HW6Constants.LIBRARY_FILE_PROP);
        if (libraryFile == null) {
            System.err.println("Error: '" + HW6Constants.LIBRARY_FILE_PROP + "' property not found.");
            return;
        }

        String d = p.getProperty(HW6Constants.DELIMITER_PROP);
        if (d == null) {
            System.err.println("Error: '" + HW6Constants.DELIMITER_PROP + "' property not found.");
            return;
        }

        hw6.loadData(libraryFile, d);

        hw6.printNaturalOrderBooks();
        hw6.printByTitleBooks();
        hw6.printByEditorJournals();
    }

    private Properties init(String[] args) {
        if (args.length != HW6Constants.NUM_ARGS) {
            System.err.println("Error: exactly " + HW6Constants.NUM_ARGS + " command line argument required.");
            System.err.println("Usage: HW6 <propertiesFile>");
            System.exit(1);
        }

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(args[0])) {
            props.load(fis);
        } catch (FileNotFoundException e) {
            System.err.println("Error: properties file not found: " + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            System.exit(1);
        }

        String lf = props.getProperty(HW6Constants.LOG_FILE_PROP);
        if (lf == null) {
            System.err.println("Error: '" + HW6Constants.LOG_FILE_PROP + "' property not found.");
            System.exit(1);
        }

        try {
            PrintStream ps = new PrintStream(new FileOutputStream(lf));
            System.setOut(ps);
            System.setErr(ps);
        } catch (FileNotFoundException e) {
            System.err.println("Error: cannot open log file: " + lf);
            System.exit(1);
        }

        props.list(System.out);
        return props;
    }

    private void loadData(String filename, String delimiter) {
        bookTreeSet    = new TreeSet<>();
        journalTreeSet = new TreeSet<>();
        bookList       = new ArrayList<>();
        journalList    = new ArrayList<>();

        System.out.println("\n*********** LOADING DATA FILE: " + filename + " ***********");

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    System.out.println("Skipping empty line.");
                    continue;
                }

                String[] fields = line.split(delimiter);

                if (fields.length == 0 || fields[0].trim().isEmpty()) {
                    System.err.println("Invalid line -- empty or unrecognized: [" + line + "]");
                    continue;
                }

                try {
                    if (fields[0].equals(HW6Constants.BOOK_TYPE)
                            && fields.length == HW6Constants.BOOK_FIELD_COUNT) {

                        String title  = fields[1].trim();
                        String author = fields[2].trim();
                        int pages     = Integer.parseInt(fields[3].trim());
                        StatusType status = StatusType.valueOf(fields[4].trim());

                        Book book = new Book(title, author, pages, status);
                        bookList.add(book);
                        boolean added = bookTreeSet.add(book);
                        System.out.println("bookTreeSet.add(" + book + ") returned: " + added);

                    } else if (fields[0].equals(HW6Constants.JOURNAL_TYPE)
                            && fields.length == HW6Constants.JOURNAL_FIELD_COUNT) {

                        String title  = fields[1].trim();
                        String editor = fields[2].trim();
                        int volume    = Integer.parseInt(fields[3].trim());
                        StatusType status = StatusType.valueOf(fields[4].trim());

                        Journal journal = new Journal(title, editor, volume, status);
                        journalList.add(journal);
                        boolean added = journalTreeSet.add(journal);
                        System.out.println("journalTreeSet.add(" + journal + ") returned: " + added);

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

        System.out.println("\nBooks put into natural-order TreeSet: " + bookTreeSet.size());
        System.out.println("Journals put into natural-order TreeSet: " + journalTreeSet.size());
    }

    private void printNaturalOrderBooks() {
        System.out.println("\n*********** BOOK TREESET -- NATURAL ORDER (author, then title) ***********");
        for (Book b : bookTreeSet) {
            System.out.println("  " + b);
        }
    }

    private void printByTitleBooks() {
        System.out.println("\n*********** BOOK TREESET -- ORDERED BY TITLE (BookTitle Comparator) ***********");
        Set<Book> treeTitle = new TreeSet<>(new BookTitle());
        for (Book b : bookList) {
            boolean added = treeTitle.add(b);
            System.out.println("treeTitle.add(" + b + ") returned: " + added);
        }
        System.out.println("\nBooks ordered by title:");
        for (Book b : treeTitle) {
            System.out.println("  " + b);
        }
    }

    private void printByEditorJournals() {
        System.out.println("\n*********** JOURNAL TREESET -- ORDERED BY EDITOR (JournalEditor Comparator) ***********");
        Set<Journal> treeEditor = new TreeSet<>(new JournalEditor());
        for (Journal j : journalList) {
            boolean added = treeEditor.add(j);
            System.out.println("treeEditor.add(" + j + ") returned: " + added);
        }
        System.out.println("\nJournals ordered by editor:");
        for (Journal j : treeEditor) {
            System.out.println("  " + j);
        }
    }
}
