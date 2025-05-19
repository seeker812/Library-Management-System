package org.example.lending;

public class NotAvailableCopyException extends Exception {

    public NotAvailableCopyException(String isbn) {
        super("No copies available for the book with ISBN " + isbn + ".");
    }
    public NotAvailableCopyException(String message, Throwable cause) {
        super(message, cause);
    }
}

