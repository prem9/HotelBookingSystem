package com.example.hotel.model;

public class Room {
    private int roomNumber;
    private String type;
    private int price;
    private boolean booked;

    public Room(int roomNumber, String type, int price, boolean booked) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.booked = booked;
    }

    // Getters and Setters for the field
       // Getters
       public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return "Room{" +
            "roomNumber=" + roomNumber +
            ", type='" + type + '\'' +
            ", price=" + price +
            ", booked=" + booked +
            '}';
    }
}
