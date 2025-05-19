package org.example.reservation;

import org.example.book.Copy;
import org.example.patron.Patron;
import org.example.patron.PatronManagement;

import java.util.*;

public class ReservationManager {
    private final Map<String, Queue<String>> reservationQueues;
    private final PatronManagement patronManagement;
    public ReservationManager(PatronManagement patronManagement) {
        this.reservationQueues = new HashMap<>();
        this.patronManagement = patronManagement;
    }
    public boolean addReservation(String isbn, String patronId) {
        reservationQueues.computeIfAbsent(isbn, k -> new LinkedList<>());
        Queue<String> queue = reservationQueues.get(isbn);
        if (!queue.contains(patronId)) {
            queue.offer(patronId);
            return true;

        }
        return false;
    }

    public boolean hasReservation(String isbn) {
        Queue<String> queue = reservationQueues.get(isbn);
        return queue != null;
    }

    public void notifyNextUser(String isbn, Copy copy) {
        Queue<String> queue = reservationQueues.get(isbn);
        if (queue != null && !queue.isEmpty()) {
            String nextUserId = queue.poll();
            Patron patron = patronManagement.getPatron(nextUserId);
            patron.addNotification("📗 Book with ISBN " + isbn + " is now available for you in branch " + copy.getBranch().toString()  +".");
            System.out.println("🔔 Notification added to user " + nextUserId + "'s inbox.");
        }
        else {
            System.out.println("⚠️ No more users reserved book " + isbn);
        }
    }
}
