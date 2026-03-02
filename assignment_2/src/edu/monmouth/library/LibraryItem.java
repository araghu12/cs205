package edu.monmouth.library;

public interface LibraryItem {
    String getTitle();
    boolean isAvailable();
    void borrowItem();
    void returnItem();
}