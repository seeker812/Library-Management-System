package org.example.inventory;
import org.example.book.Book;
import org.example.book.BookCopy;
import org.example.book.Copy;
import org.example.book.PaperBook;
import org.example.branch.Branch;

import java.util.*;
import java.util.stream.Collectors;

public class BookInventoryManagement {
    private Map<String, Book> booksByIsbn;
    private Map<String, CopyPool> copyPools;

    public BookInventoryManagement() {
        this.booksByIsbn = new HashMap<>();
        this.copyPools = new HashMap<>();
    }
    public void registerBook(Book book) {
        booksByIsbn.putIfAbsent(book.getISBN(), book);
    }

    private void increaseCopyCount(Book book, int numberOfCopies) {
        if(book instanceof PaperBook paperBook){
            paperBook.addCopies(numberOfCopies);
        }
    }


    public void addCopy(Copy copy, int numberOfCopies) {
        String isbn = copy.getISBN();
        Book book = copy.getBook();
        String shelfLocation = copy.getShelfLocation();
        Branch branch = copy.getBranch();
        registerBook(book);
        increaseCopyCount(book,numberOfCopies);

        CopyPool pool = copyPools.computeIfAbsent(isbn, k -> new CopyPool());
        pool.add(copy);
        for (int i = 1; i < numberOfCopies; i++) {
            // Create a new Copy each time with a unique ID
            Copy newCopy = new BookCopy(shelfLocation,book,branch);
            pool.add(newCopy);
        }

        System.out.println("Copy added successfully.");

    }

    public void removeBook(String isbn) {
       Book removedBook = booksByIsbn.remove(isbn);
       copyPools.remove(isbn);
        if (removedBook != null){
            System.out.println("Book removed successfully.");
        }else{
            System.out.println("Book not found.");
        }
    }

    public Optional<Book> searchByIsbn(String isbn) {
        return Optional.ofNullable(booksByIsbn.get(isbn));
    }

    public List<Book> searchByTitle(String title) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Copy> getAvailableCopies(String isbn) {
        CopyPool pool = copyPools.get(isbn);
        return pool != null ? pool.getAvailable() : List.of();
    }

    public List<Copy> getBorrowedCopies(String isbn) {
        CopyPool pool = copyPools.get(isbn);
        return pool != null ? pool.getBorrowed() : List.of();
    }

    public CopyPool getCopyPool(String isbn) {
        CopyPool pool = copyPools.get(isbn);
        return pool;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(booksByIsbn.values());
    }
}
