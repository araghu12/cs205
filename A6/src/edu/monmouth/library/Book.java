package edu.monmouth.library;

public class Book implements LibraryItem, Comparable<Book> {

    private String title;
    private String author;
    private int pages;
    private StatusType status;

    public Book(String title, String author, int pages, StatusType status) throws BookException {
        setTitle(title);
        setAuthor(author);
        setPages(pages);
        setStatus(status);
    }

    public void setTitle(String title) throws BookException {
        if (title == null || title.length() < BookC.MIN_TITLE_LENGTH)
            throw new BookException("Title is null or empty.");
        this.title = title;
    }

    @Override
    public String getTitle() { return title; }

    public void setAuthor(String author) throws BookException {
        if (author == null || author.length() < BookC.MIN_AUTHOR_LENGTH)
            throw new BookException("Author is null or empty.");
        this.author = author;
    }

    public String getAuthor() { return author; }

    public void setPages(int pages) throws BookException {
        if (pages < BookC.MIN_PAGES)
            throw new BookException("The book pages must be at least " + BookC.MIN_PAGES + ".");
        this.pages = pages;
    }

    public int getPages() { return pages; }

    public void setStatus(StatusType status) { this.status = status; }

    public StatusType getStatus() { return status; }

    @Override
    public boolean isAvailable() { return status == StatusType.ONSHELF; }

    @Override
    public void borrowItem() { status = StatusType.BORROWED; }

    @Override
    public void returnItem() { status = StatusType.ONSHELF; }

    @Override
    public int compareTo(Book other) {
        System.out.println("Book.compareTo() invoked: [" + author + " / " + title + "] vs [" + other.author + " / " + other.title + "]");
        int authorCmp = this.author.compareTo(other.author);
        if (authorCmp != 0) return authorCmp;
        return this.title.compareTo(other.title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return author.equals(other.author) && title.equals(other.title);
    }

    @Override
    public int hashCode() {
        System.out.println("Book.hashCode() used for: " + title + " by " + author);
        int result = 1;
        result = 31 * result + author.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author
                + ", pages=" + pages + ", status=" + status + "]";
    }
}
