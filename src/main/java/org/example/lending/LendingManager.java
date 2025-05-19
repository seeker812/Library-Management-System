package org.example.lending;

import org.example.book.Copy;
import org.example.inventory.BookInventoryManagement;
import org.example.inventory.CopyPool;
import org.example.patron.Patron;
import org.example.reservation.ReservationManager;

import java.time.LocalDate;


public class LendingManager {

    private final BookInventoryManagement bookInventoryManagement;
    private final ReservationManager reservationManager;
    public LendingManager(BookInventoryManagement bookInventoryManagement,ReservationManager reservationManager) {
        this.bookInventoryManagement = bookInventoryManagement;
        this.reservationManager = reservationManager;
    }

    public Copy checkoutBook(String isbn, Patron patron) throws NotAvailableCopyException, BookNotFoundException {
        CopyPool copypool = bookInventoryManagement.getCopyPool(isbn);
        if (copypool == null) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }
        Copy copy = copypool.getFirstAvailable();
        if (copy == null){
            throw new NotAvailableCopyException(isbn);
        }
        copypool.remove(copy);
        copy.setAvailable(false);
        copy.setBorrower(patron);
        copypool.add(copy);
        copy.setCheckoutDate(LocalDate.now());
        return copy;
    }

    public boolean returnBook(String isbn, int copyId, Patron patron) {
        CopyPool copypool = bookInventoryManagement.getCopyPool(isbn);
        Copy copy = copypool.getBorrowedCopy(copyId);
        if (copy == null) {
            System.out.println("Copy with ID " + copyId + " not found.");
            return false;
        }
        if (copy.getBorrower() != patron) {
            System.out.println("Copy with ID " + copyId + " is not borrowed by patron " + patron.getId() + ".");
            return false;
        }
        if (copy.getCheckoutDate() == null) {
            System.out.println("Copy with ID " + copyId + " has not been checked out.");
            return false;
        }
        else {
            copy.setBorrower(null);
            copy.setCheckoutDate(null);
            copypool.remove(copy);
            copy.setAvailable(true);
            copypool.add(copy);
        }
        if(reservationManager.hasReservation(isbn))
            reservationManager.notifyNextUser(isbn,copy);
        return true;

    }
    public BookInventoryManagement getBookInventoryManagement() {
        return bookInventoryManagement;
    }

    public ReservationManager getReservationManager() {
        return reservationManager;
    }
}
