package edu.monmouth.library;

public class LibraryNode {
    private LibraryItem info;
    private LibraryNode next;

    public LibraryNode(LibraryItem info) {
        this.info = info;
        this.next = null;
    }

    public void setInfo(LibraryItem info) {
        this.info = info;
    }

    public LibraryItem getInfo() {
        return info;
    }

    public void setNext(LibraryNode link) {
        this.next = link;
    }

    public LibraryNode getNext() {
        return next;
    }

    @Override
    public String toString() {
    	return info != null ? info.toString() : "null";        
    }
}