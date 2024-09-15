package com.example.hotel;

import com.example.hotel.dao.HotelDaoImpl;
import com.example.hotel.model.Room;
import com.example.hotel.service.Hotel;
import org.h2.jdbcx.JdbcDataSource;


import javax.sql.DataSource;
import java.util.List;

public class App {

    private static final String H2_DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    public static void main(String[] args) throws Exception {

        DataSource dataSource = createDataSource();
        deleteSchema(dataSource);
        createSchema(dataSource);
        HotelDaoImpl dao = new HotelDaoImpl(dataSource);

        // Add rooms to database
        addRooms(dao);

        // Print room booking status
        getRoomStatus(dao);

        Hotel hotel = new Hotel(dao);

        // Book rooms
        hotel.bookRoom(1);
        hotel.bookRoom(2);
        hotel.bookRoom(3);

        // Cancel bookings for some rooms
        hotel.cancelRoomBooking(1);
        hotel.cancelRoomBooking(3);

        // Print room booking status after cancellation
        getRoomStatus(dao);

        deleteSchema(dataSource);
    }
/* 
    private static void getRoomStatus(HotelDaoImpl dao) throws Exception {
        try (var roomStream = dao.getAll()) {
            roomStream.forEach(room -> System.out.println(room));
        }
    }
*/
    private static void getRoomStatus(HotelDaoImpl dao) throws Exception {
        List<Room> rooms = dao.getAll();
        for (Room room : rooms) {
            System.out.println(room);
        }
    }
    

    private static void deleteSchema(DataSource dataSource) throws java.sql.SQLException {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS Room");
        }
    }

    private static void createSchema(DataSource dataSource) throws Exception {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("CREATE TABLE Room (roomNumber INT PRIMARY KEY, type VARCHAR(50), price INT, booked BOOLEAN)");
        }
    }

    private static DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(H2_DB_URL);
        return dataSource;
    }

    private static void addRooms(HotelDaoImpl hotelDao) throws Exception {
        List<Room> rooms = List.of(
                new Room(1, "Single", 50, false),
                new Room(2, "Double", 80, false),
                new Room(3, "Queen", 120, false),
                new Room(4, "King", 150, false),
                new Room(5, "Single", 50, false),
                new Room(6, "Double", 80, false)
        );
        for (Room room : rooms) {
            hotelDao.add(room);
        }
    }
}
