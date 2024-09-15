package com.example.hotel.service;

import com.example.hotel.dao.HotelDaoImpl;
import com.example.hotel.model.Room;

import java.util.Optional;

public class Hotel {

    private final HotelDaoImpl hotelDao;

    public Hotel(HotelDaoImpl hotelDao) {
        this.hotelDao = hotelDao;
    }

    public void bookRoom(int roomNumber) throws Exception {
        Optional<Room> room = hotelDao.getById(roomNumber);

        if (room.isEmpty()) {
            throw new Exception("Room number: " + roomNumber + " does not exist");
        } else {
            if (room.get().isBooked()) {
                throw new Exception("Room already booked!");
            } else {
                Room updateRoomBooking = room.get();
                updateRoomBooking.setBooked(true);
                System.out.println("Booking  done for room number: " + roomNumber);
                System.out.println("Price: " + updateRoomBooking.getPrice());
                hotelDao.update(updateRoomBooking);
            }
        }
    }

    public void cancelRoomBooking(int roomNumber) throws Exception {
        Optional<Room> room = hotelDao.getById(roomNumber);

        if (room.isEmpty()) {
            throw new Exception("Room number: " + roomNumber + " does not exist");
        } else {
            if (room.get().isBooked()) {
                Room updateRoomBooking = room.get();
                updateRoomBooking.setBooked(false);
                hotelDao.update(updateRoomBooking);

                System.out.println("Booking cancelled for room number: " + roomNumber);
                System.out.println("Refund amount: " + updateRoomBooking.getPrice());
            } else {
                throw new Exception("No booking for the room exists");
            }
        }
    }
}

