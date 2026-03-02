package edu.monmouth.library;

public class ListLibraryNode {
    private LibraryNode head;

    public ListLibraryNode() {
        head = null;
    }

    public LibraryNode removeFirst() {
        if (isEmpty()) return null;
        LibraryNode first = head;
        head = head.getNext();
        return first;
    }

    public LibraryNode first() {
        return head;
    }

    public LibraryNode last() {
        if (isEmpty()) return null;
        LibraryNode current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }

    public void insert(LibraryItem element) {
        LibraryNode newNode = new LibraryNode(element);
        newNode.setNext(head);
        head = newNode;
    }

    public void insertEnd(LibraryItem element) {
        LibraryNode newNode = new LibraryNode(element);
        if (isEmpty()) {
            head = newNode;
        } else {
            last().setNext(newNode);
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int count = 0;
        LibraryNode current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public void clear() {
        head = null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        LibraryNode current = head;
        while (current != null) {
            result.append(current.toString()).append("\n");
            current = current.getNext();
        }
        return result.toString();
    }
}