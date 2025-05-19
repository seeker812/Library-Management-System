package org.example.branch;
import org.example.book.Book;
import org.example.book.Copy;
import org.example.inventory.BookInventoryManagement;
import org.example.lending.LendingManager;
import org.example.patron.Patron;
import org.example.patron.PatronManagement;
import org.example.reservation.ReservationManager;

import java.util.*;
import java.util.stream.Collectors;

public class BranchManagement {

    Map<String,Branch> branches;
    private final ReservationManager reservationManager;

    public BranchManagement(ReservationManager reservationManager) {
        this.branches = new HashMap<>();
        this.reservationManager = reservationManager;
    }

    public Branch addBranch(String branchId, String name, String location) {
        BookInventoryManagement bookInventoryManagement = new BookInventoryManagement();
        LendingManager lendingManager = new LendingManager(bookInventoryManagement,reservationManager);
        if( branches.containsKey(branchId) ){
            System.out.println("Branch with ID " + branchId + " already exists.");
            return null;
        }
        Branch branch = new Branch(branchId,name,location,lendingManager);
        branches.putIfAbsent(branch.getBranchId(),branch);
        return branch;
    }
    public void removeBranch(String branchId) {
        branches.remove(branchId);
    }
    public Branch getBranch(String branchId) {
        return branches.get(branchId);
    }

    public List<Branch> getBranches() {
        return new ArrayList<>(branches.values());
    }

    public Branch getBranchById(String branchId) {
        return branches.get(branchId);
    }


}
