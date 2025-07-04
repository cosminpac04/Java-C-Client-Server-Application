package motorcycle.database;

import java.sql.*;

public class DataBaseConnection {
//    private static final String URL = "jdbc:sqlite:test.sqlite";
    private static final String URL = "jdbc:sqlite:D:\\Facultate\\An 2 sem2\\Mpp\\Proiect java\\project-java-cosminpac04\\test.sqlite";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            System.out.println("Connected to the database successfully");
            System.out.println("Connecting to database at: " + URL);
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load JDBC driver");
        } catch (SQLException e) {
            System.err.println("Could not connect to database");
        }
        return conn;
    }
    public static void listTables() {
        String query = "SELECT name FROM sqlite_master WHERE type='table';";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Tables in the database:");
            while (rs.next()) {
                System.out.println("Table: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void listParticipants() {
        String query = "SELECT * FROM Participant";  // Adjust this for your table name

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Participants in the database:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Pid") + ", Name: " + rs.getString("Name") +
                        ", Engine Capacity: " + rs.getInt("EngineCapacity") +
                        ", Team: " + rs.getString("Team"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
