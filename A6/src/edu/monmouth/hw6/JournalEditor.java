package edu.monmouth.hw6;

import edu.monmouth.library.Journal;
import java.util.Comparator;

public class JournalEditor implements Comparator<Journal> {

    @Override
    public int compare(Journal journal1, Journal journal2) {
        System.out.println("JournalEditor.compare() invoked: [" + journal1.getEditor() + "] vs [" + journal2.getEditor() + "]");
        return journal1.getEditor().compareTo(journal2.getEditor());
    }
}
