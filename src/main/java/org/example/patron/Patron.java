package org.example.patron;

import org.example.branch.Branch;

import java.util.ArrayList;
import java.util.List;

public class Patron {
    private final String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private final List<String> notifications;

    public Patron(String id, String name, String email, String phone, String address, String city, String state, String zip, String country) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.notifications = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void showAllNotifications() {
        notifications.forEach(System.out::println);
    }
    public void addNotification(String notification) {
        notifications.add(notification);
    }
    public void showLastNotification() {
        if (notifications.isEmpty()) {
            System.out.println("No notifications available.");
        } else {
            System.out.println(notifications.get(notifications.size() - 1));
        }
    }
    public void clearNotifications() {
        notifications.clear();
    }
    @Override
    public String toString() {
        return "Patron{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' + '}';
    }
}
