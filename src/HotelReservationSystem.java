import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem{
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";

    private static final String username = "root";

    private static final String password = "Root@123";

    public static void main (String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
    }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                    System.out.println("1. Reserve A Room");
                    System.out.println("2. View Reservation");
                    System.out.println("3. Get Room Number");
                    System.out.println("4. Update Reservations");
                    System.out.println("5. Delete Reservations");
                    System.out.println("0. Exit");
                    System.out.println();
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            reserveRoom(connection, sc);
                            break;
                        case 2:
                            viewReservations(connection);
                            break;
                        case 3:
                            getRoomNumber(connection, sc);
                            break;
                        case 4:
                            updateReservations(connection, sc);
                            break;
                        case 5:
                            deleteReservations(connection, sc);
                            break;
                        case 0:
                            exit();
                            sc.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please Try Again! ");

                    }

                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
    }
    private static void reserveRoom(Connection connection, Scanner sc) {
        try {
            sc.nextLine();
            System.out.println("Enter guest name: ");
            String guestName = sc.nextLine();

            System.out.println("Enter room number: ");
            int roomNumber = sc.nextInt();
            System.out.println("Enter contact number: ");
            sc.nextLine();
            String contactNumber = sc.nextLine();

            String sql = "INSERT INTO Reservations(guest_name, room_number, contact_number) " +
                    "VALUES ('" + guestName + "', " + roomNumber + ", '" + contactNumber + "')";

            try(Statement statement= connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation Successful!");

                } else {
                    System.out.println("Reservation Failed.");
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection connection) throws SQLException {
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM Reservations";
        try (Statement statement= connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            System.out.println("Current Reservations:");
            while (rs.next()) {
                int reservationId = rs.getInt("reservation_id");
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String contactNumber = rs.getString("contact_number");
                String reservationDate = rs.getTimestamp("reservation_date").toString();

                System.out.println("ID: " + reservationId + ", Name: " + guestName + ", Room: " + roomNumber +
                        ", Contact: " + contactNumber + ", Date: " + reservationDate);
            }

        }
    }

    public static void getRoomNumber(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter reservation ID: ");
            int reservationID = sc.nextInt();
            System.out.println("Enter guest name: ");
            String guestName = sc.next();

            String sql = "SELECT room_number FROM Reservations WHERE reservation_id = " + reservationID +
                    " AND guest_name = '" + guestName + "'";

            try (Statement statement= connection.createStatement();
            ResultSet rs=statement.executeQuery(sql)){
                if (rs.next()){
                    int roomNumber = rs.getInt("room_number");
                    System.out.println("Room number for reservation id : "+ reservationID + " is: " + roomNumber);

                }else {
                    System.out.println("Reservation not found for given id.");
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateReservations(Connection connection, Scanner sc) {
        try{
            System.out.println("Enter reservation id to update ;");
            int reservationID = sc.nextInt();
            sc.nextLine();
            if (!reservationExists(connection, reservationID)){
                System.out.println("Reservation not found for given ID");
                return;
            }
            System.out.println("Enter new guest name : ");
            String newGuestName = sc.nextLine();
            System.out.println("Enter new room number : ");
            int newRoomNumber = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter new contact number ; ");
            String newContactNumber = sc.nextLine();

            String sql = "UPDATE Reservations SET guest_name = '" + newGuestName + "', "
                    + "room_number = " + newRoomNumber + ", "
                    + "contact_number = '" + newContactNumber + "' "
                    + "WHERE reservation_id = " +reservationID;
            try (Statement statement=connection.createStatement()){
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows>0){
                    System.out.println("Reservation updated successfully!");

                }else {
                    System.out.println("Reservation updated failed.");
                }

            }





        }catch (SQLException e){
            e.printStackTrace();
        }

        // Logic to update reservation details
    }

    public static void deleteReservations(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID to delete: ");
            int reservationId = scanner.nextInt();

            if (!reservationExists(connection, reservationId)){
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            String sql = "DELETE FROM Reservations WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation deleted successfully!");
                } else {
                    System.out.println("Reservation deletion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT reservation_id FROM Reservations WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                return resultSet.next(); // If there's a result, the reservation exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle database errors as needed
        }
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while (i > 0) {
        System.out.print(".");
        Thread.sleep(450);
        i--;
        }
        System.out.println();
        System.out.println("Thank You For Using Hotel Reservation System!!!");
    }
}
