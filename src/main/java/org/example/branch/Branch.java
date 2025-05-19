package org.example.branch;

import org.example.book.Copy;
import org.example.inventory.BookInventoryManagement;
import org.example.lending.LendingManager;
import org.example.reservation.ReservationManager;

public class Branch {
    private final String branchId;
    private final String name;
    private final String location;
    private final LendingManager lendingManager;
    public Branch(String branchId, String name, String location, LendingManager lendingManager) {
        this.branchId = branchId;
        this.name = name;
        this.location = location;
        this.lendingManager = lendingManager;
    }

    public String getBranchId() { return branchId; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public LendingManager getLendingManager() { return lendingManager; }
    public BookInventoryManagement getBookInventoryManagement() { return lendingManager.getBookInventoryManagement(); }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId='" + branchId + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

}
