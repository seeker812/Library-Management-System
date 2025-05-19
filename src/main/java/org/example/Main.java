package org.example;

import org.example.book.Book;
import org.example.book.BookCopy;
import org.example.book.Copy;
import org.example.book.PaperBook;
import org.example.branch.Branch;
import org.example.branch.BranchManagement;
import org.example.inventory.BookInventoryManagement;
import org.example.inventory.CopyPool;
import org.example.lending.LendingManager;
import org.example.patron.Patron;
import org.example.patron.PatronManagement;
import org.example.reservation.ReservationManager;
import org.example.lending.BookNotFoundException;
import org.example.lending.NotAvailableCopyException;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static PatronManagement patronManagement = new PatronManagement();
    private static ReservationManager reservationManager = new ReservationManager(patronManagement);
    private static BranchManagement branchManagement = new BranchManagement(reservationManager);

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        branchManagement.addBranch("1", "Library - Universe", "123 Main St");
        branchManagement.addBranch("2", "Library - World", "456 Main St");
        branchManagement.addBranch("3", "Library Paradise", "789 Main St");

        while (true) {
            System.out.println("\n=== Top Library Management System ===");
            System.out.println("1. Branch Login");
            System.out.println("2. Add Branch");
            System.out.println("3. Patron Login");
            System.out.println("4. Patron Manager");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> branchLogin();
                case "2" -> addBranch();
                case "3" -> patronLogin();
                case "4" -> patronManagerMenu();
                case "5" -> {
                    System.out.println("Exiting system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void branchLogin() {
        System.out.print("Enter Branch ID to login: ");
        String branchId = scanner.nextLine();
        Branch branch = branchManagement.getBranchById(branchId);
        if (branch == null) {
            System.out.println("Branch not found.");
            return;
        }
        System.out.println("Logged in to branch: " + branch.getName());

        LendingManager lendingManager = branch.getLendingManager();
        BookInventoryManagement inventory = branch.getBookInventoryManagement();

        while (true) {
            System.out.println("\n-- Branch Operations --");
            System.out.println("1. Add Book");
            System.out.println("2. Add Copies of a Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Checkout Book");
            System.out.println("5. Return Book");
            System.out.println("6. View Inventory");
            System.out.println("7. Logout Branch");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addBook(inventory, branch);
                case "2" -> addCopies(inventory, branch);
                case "3" -> removeBook(inventory);
                case "4" -> checkoutBook(lendingManager);
                case "5" -> returnBook(lendingManager);
                case "6" -> viewInventory(inventory);
                case "7" -> {
                    System.out.println("Logging out from branch.");
                    return;  // Exit branch menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addBook(BookInventoryManagement inventory, Branch branch) {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter Year Published: ");
        int yearPublished = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Number of Pages: ");
        int numberOfPages = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Genre (e.g. Science Fiction, Fantasy, etc.): ");
        String genre = scanner.nextLine();
        System.out.print("Enter Shelf Location (e.g. A, B, C, D, etc.): ");
        String shelfLocation = scanner.nextLine();
        System.out.print("Number of copies (e.g. 1, 2, 3, etc.): ");
        int numberOfCopies = Integer.parseInt(scanner.nextLine());
        // Assuming PaperBook constructor: PaperBook(String isbn, String title, String author)
        PaperBook book = new PaperBook(title,author,genre,"PAPER",yearPublished,isbn,numberOfPages);
        Copy copy = new BookCopy(shelfLocation,book , branch);
        inventory.addCopy(copy,numberOfCopies);
        System.out.println("Book added successfully in the inventory");
    }

    private static void addCopies(BookInventoryManagement inventory, Branch branch) {
        System.out.print("Enter ISBN of the book to add copies: ");
        String isbn = scanner.nextLine();

        // Search for book
        Optional<Book> optionalBook = inventory.searchByIsbn(isbn);
        if (optionalBook.isEmpty()) {
            System.out.println("Book not found.");
            return;
        }

        System.out.print("Enter number of copies to add: ");
        int copies = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter shelf location (e.g. A, B, C, D, etc.):");
        String shelfLocation = scanner.nextLine();
        var book = optionalBook.get();
        Copy copy = new BookCopy(shelfLocation,book , branch);
        inventory.addCopy(copy, copies);
        System.out.println(copy.toString() + " copies added.");
    }

    private static void removeBook(BookInventoryManagement inventory) {
        System.out.print("Enter ISBN to remove: ");
        String isbn = scanner.nextLine();
        inventory.removeBook(isbn);
    }

    private static void checkoutBook(LendingManager lendingManager) {
        System.out.print("Enter ISBN to checkout: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();

        Patron patron = patronManagement.getPatron(patronId);
        if (patron == null) {
            System.out.println("Patron not found.");
            return;
        }

        try {
            Copy copy = lendingManager.checkoutBook(isbn, patron);
            if (copy != null) {
                System.out.println("Book checked out successfully. Copy ID: " + copy.getCopyId());
            } else {
                System.out.println("No available copies to checkout.");
            }
        } catch (NotAvailableCopyException e) {
            System.out.println(e.getMessage());
            handleNoAvailableCopy(lendingManager, isbn, patronId);
        }
        catch (BookNotFoundException e) {
            System.out.println("Book not available for checkout.");
        }
        catch (Exception e) {
            System.out.println("Error during checkout: " + e.getMessage());
        }
    }

    private static void handleNoAvailableCopy(LendingManager lendingManager, String isbn, String patronId) {
        System.out.println("No available copies for book with ISBN " + isbn);
        System.out.println("Would you like to reserve this book?");
        System.out.println("1. Yes, reserve it");
        System.out.println("2. No, go back");

        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            boolean success = reservationManager.addReservation(isbn, patronId);
            if (success) {
                System.out.println("Book reserved successfully.");
            } else {
                System.out.println("You have already reserved this book.");
            }
        } else {
            System.out.println("Returning to previous menu.");
        }
    }

    private static void returnBook(LendingManager lendingManager) {
        System.out.print("Enter Copy ID to return: ");
        int copyId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();

        Patron patron = patronManagement.getPatron(patronId);
        if (patron == null) {
            System.out.println("Patron not found.");
            return;
        }

        boolean success = lendingManager.returnBook(isbn,copyId, patron);
        if (success) {
            System.out.println("Book returned successfully.");
        }
        else {
            System.out.println("Copy ID not found or copy is not available for return.");
        }


    }

    private static void viewInventory(BookInventoryManagement inventory) {
        System.out.println("-- Inventory --");
        for (Book book : inventory.getBooks()) {
            System.out.println(book.toString());
            String isbn = book.getISBN();
            CopyPool copyPool = inventory.getCopyPool(isbn);
            if (copyPool != null) {
                System.out.println("  Available copies: " + copyPool.getAvailable().size());
                System.out.println("  Borrowed copies: " + copyPool.getBorrowed().size());
            } else {
                System.out.println("  No copies available.");
            }
        }
    }

    private static void addBranch() {
        System.out.print("Enter Branch ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Branch Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Branch Location: ");
        String location = scanner.nextLine();

        Branch branch = branchManagement.addBranch(id, name, location);
        System.out.println("Branch added successfully: " + branch.getName());
    }

    private static void patronLogin() {
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();
        Patron patron = patronManagement.getPatron(patronId);
        if (patron == null) {
            System.out.println("Patron not found.");
            return;
        }
        System.out.println("Welcome, " + patron.getName());
        showPatronMenu(patron);
        // Extend with patron specific operations (view notifications, borrow books etc.)
    }

    private static void showPatronMenu(Patron patron) {
        while (true) {
            System.out.println("\n-- Patron Menu --");
            System.out.println("1. View Notifications");
            System.out.println("2. View Borrowing History");
            System.out.println("3. Back");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewNotifications(patron);
//                case "2" -> viewBorrowingHistory(patron);
                case "3" -> {
                    System.out.println("Returning to main menu.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewNotifications(Patron patron) {
        System.out.println("\n-- Notifications --");
        patron.showAllNotifications();
    }

//    private static void viewBorrowingHistory(Patron patron) {
//        System.out.println("\n-- Borrowing History --");
//        List<Copy> history = patron.getBorrowingHistory();
//        if (history.isEmpty()) {
//            System.out.println("No borrowing history found.");
//        } else {
//            for (Copy copy : history) {
//                System.out.println("Copy ID: " + copy.getCopyId() +
//                        ", Book: " + copy.getBook().getTitle() +
//                        ", Checkout Date: " + copy.getCheckoutDate());
//            }
//        }
//    }

    private static void patronManagerMenu() {
        while (true) {
            System.out.println("\n-- Patron Manager --");
            System.out.println("1. Add Patron");
            System.out.println("2. Remove Patron");
            System.out.println("3. List Patrons");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addPatron();
                case "2" -> removePatron();
                case "3" -> listPatrons();
                case "4" -> {
                    return;  // back to main menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addPatron() {
        System.out.print("Enter Patron ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter State: ");
        String state = scanner.nextLine();
        System.out.print("Enter Zip: ");
        String zip = scanner.nextLine();
        System.out.print("Enter Country: ");
        String country = scanner.nextLine();

        Patron patron = new Patron(id, name, email, phone, address, city, state, zip, country);
        patronManagement.addPatron(patron);
        System.out.println("Patron added successfully: " + name);
    }

    private static void removePatron() {
        System.out.print("Enter Patron ID to remove: ");
        String id = scanner.nextLine();
        patronManagement.removePatron(id);
        System.out.println("Patron removed (if existed).");
    }

    private static void listPatrons() {
        System.out.println("-- List of Patrons --");
        patronManagement.getPatronRepository().values().forEach(p ->
                System.out.println(p.getId() + ": " + p.getName())
        );
    }
}
