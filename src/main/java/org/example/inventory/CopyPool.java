package org.example.inventory;
import java.util.*;
import org.example.book.Copy;

public class CopyPool {
    private List<Copy> available = new ArrayList<>();
    private List<Copy> borrowed = new ArrayList<>();

    public void add(Copy copy) {
        if (copy.isAvailable()) available.add(copy);
        else borrowed.add(copy);
    }

    public void remove(Copy copy) {
        if (copy.isAvailable()) available.remove(copy);
        else borrowed.remove(copy);
    }
    public void clear() {
        available.clear();
        borrowed.clear();
    }

    public Copy getFirstAvailable() {
        return available.isEmpty() ? null : available.get(0);
    }
    public boolean isAvailable(){
        return ! available.isEmpty();
    }
    public Copy getBorrowedCopy(int copyId) {
        for (Copy copy : borrowed) {
            if (copy.getCopyId() == copyId) return copy;
        }
        return null;
    }

    public List<Copy> getAvailable() { return available; }
    public List<Copy> getBorrowed() { return borrowed; }
}
