package edu.monmouth.hw6;

import edu.monmouth.library.Book;
import java.util.Comparator;

public class BookTitle implements Comparator<Book> {

    @Override
    public int compare(Book book1, Book book2) {
        System.out.println("BookTitle.compare() invoked: [" + book1.getTitle() + "] vs [" + book2.getTitle() + "]");
        return book1.getTitle().compareTo(book2.getTitle());
    }
}
