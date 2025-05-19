package org.example.book;

import org.example.branch.Branch;
import org.example.patron.Patron;

import java.time.LocalDate;

public interface Copy {
    String getISBN();
    String getTitle();
    String getAuthor();
    String getGenre();
    String getFormat();
    int getPublicationYear();
    boolean isAvailable();
    void setAvailable(boolean available);
    Book getBook();
    void setBorrower(Patron borrower);
    void setCheckoutDate(LocalDate checkoutDate);
    Patron getBorrower();
    Branch getBranch();
    void setBranch(Branch branch);
    String getShelfLocation();
    int getCopyId();
    LocalDate getCheckoutDate();
}
