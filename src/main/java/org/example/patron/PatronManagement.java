package org.example.patron;

import java.util.HashMap;
import java.util.Map;

public class PatronManagement {
    private  Map<String, Patron> patronRepository;

    public PatronManagement() {
        this.patronRepository = new HashMap<>();
    }
    public void addPatron(Patron patron) {
        if(patronRepository.containsKey(patron.getId())) {
            System.out.println("Patron with ID " + patron.getId() + " already exists.");
            return;
        }
        patronRepository.put(patron.getId(),patron);
    }
    public Patron getPatron(String id) {
        return patronRepository.get(id);
    }
    public void removePatron(String id) {
        patronRepository.remove(id);
    }
    public Map<String, Patron> getPatronRepository() {
        return patronRepository;
    }


}
