package com.example.hotel.dao;

import com.example.hotel.model.Room;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class HotelDaoImpl {

    private final DataSource dataSource;

    public HotelDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Room> getById(int roomNumber) throws Exception {
        String query = "SELECT * FROM Room WHERE roomNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, roomNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Room room = new Room(
                        rs.getInt("roomNumber"),
                        rs.getString("type"),
                        rs.getInt("price"),
                        rs.getBoolean("booked")
                );
                return Optional.of(room);
            } else {
                return Optional.empty();
            }
        }
    }

    public void update(Room room) throws Exception {
        String updateQuery = "UPDATE Room SET booked = ? WHERE roomNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setBoolean(1, room.isBooked());
            stmt.setInt(2, room.getRoomNumber());
            stmt.executeUpdate();
        }
    }
/* 
    public Stream<Room> getAll() throws Exception {
        String query = "SELECT * FROM Room";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            List<Room> rooms = Stream.generate(() -> {
                try {
                    return rs.next() ? new Room(
                            rs.getInt("roomNumber"),
                            rs.getString("type"),
                            rs.getInt("price"),
                            rs.getBoolean("booked")
                    ) : null;
                } catch (Exception e) {
                    return null;
                }
            }).limit(rs.getFetchSize()).collect(Collectors.toList());

            return rooms.stream();
        }
    } */
    public List<Room> getAll() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Room")) {
            while (resultSet.next()) {
                Room room = new Room(
                        resultSet.getInt("roomNumber"),
                        resultSet.getString("type"),
                        resultSet.getInt("price"),
                        resultSet.getBoolean("booked")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }
    

    public void add(Room room) throws Exception {
        String insertQuery = "INSERT INTO Room (roomNumber, type, price, booked) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setInt(1, room.getRoomNumber());
            stmt.setString(2, room.getType());
            stmt.setInt(3, room.getPrice());
            stmt.setBoolean(4, room.isBooked());
            stmt.executeUpdate();
        }
    }
}
