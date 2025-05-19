package org.example.book;

import org.example.branch.Branch;
import org.example.patron.Patron;

import java.time.LocalDate;

public class BookCopy implements Copy{
    private static int nextCopyId = 1;

    private final int copyId;
    private String shelfLocation;
    private boolean available;
    private LocalDate checkoutDate;
    private final Book book;
    private Patron borrower;
    private Branch branch;

    public BookCopy(String shelfLocation, Book book,Branch branch) {
        this.copyId = nextCopyId++;
        this.shelfLocation = shelfLocation;
        this.available = true;
        this.book = book;
        this.branch = branch;
    }

    @Override
    public Branch getBranch() {
        return branch;
    }
    @Override
    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    @Override
    public void setBorrower(Patron borrower) {
        this.borrower = borrower;
    }
    @Override
    public Patron getBorrower() {
        return borrower;
    }
    @Override
    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
    @Override
    public Book getBook() {
        return book;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    @Override
    public String getISBN() {
        return book.getISBN();
    }

    @Override
    public String getTitle() {
        return book.getTitle();
    }

    @Override
    public String getAuthor() {
        return book.getAuthor();
    }

    @Override
    public String getGenre() {
        return book.getGenre();
    }

    @Override
    public String getFormat() {
        return book.getFormat();
    }

    @Override
    public int getPublicationYear() {
        return book.getPublicationYear();
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public int getCopyId() {
        return copyId;
    }
    @Override
    public boolean isAvailable() {
        return available;
    }
    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }
}
