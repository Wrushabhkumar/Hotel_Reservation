import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HotelReservationSystem {
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
            Connection connection= DriverManager.getConnection(url, username, password);
            while(true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner sc= new Scanner(System.in);
                System.out.println("1. Reserve A Room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                System.out.println();
                int choice = sc.nextInt();
                switch (choice){
                    case 1:
                        reserveRoom( connection, sc);
                        break;
                    case 2:
                        viewReservations (connection);
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
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
