package org.example.book;

public class PaperBook extends Book{
    private final int pagesinBook;
    private  int totalCopies;


    public PaperBook(String title, String author, String genre, String format, int publicationYear, String ISBN,int pagesinBook) {
        super(title, author, genre, format, publicationYear,ISBN);
        this.pagesinBook = pagesinBook;
    }
    public int getPagesInBook() {
        return pagesinBook;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void addCopies(int copies) {
        this.totalCopies += copies;

    }

}
