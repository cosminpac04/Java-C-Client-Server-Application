package motorcycle.repository;

import motorcycle.database.DataBaseConnection;
import motorcycle.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IRepository<User, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (Uid, UserName, Password, Role) VALUES (?, ?, ?)";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getID());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();

            logger.info("Added user: {}", user.getUsername());

        } catch (SQLException e) {
            logger.error("Error adding user: {}", user.getUsername(), e);
        }
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE Uid = ?";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getID());
            stmt.executeUpdate();

            logger.info("Deleted user with ID: {}", user.getID());

        } catch (SQLException e) {
            logger.error("Error deleting user with ID: {}", user.getID(), e);
        }
    }

    @Override
    public void update(User user, Integer id) {
        String sql = "UPDATE users SET UserName = ?, Password = ?, Role = ? WHERE Uid = ?";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, id);
            stmt.executeUpdate();

            logger.info("Updated user with ID: {}", id);

        } catch (SQLException e) {
            logger.error("Error updating user with ID: {}", id, e);
        }
    }

    @Override
    public User findById(Integer id) {
        String sql = "SELECT * FROM users WHERE Uid = ?";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
                logger.info("Found user: {}", user.getUsername());
                return user;
            } else {
                logger.warn("No user found with ID: {}", id);
            }

        } catch (SQLException e) {
            logger.error("Error finding user with ID: {}", id, e);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role")));
            }

            logger.info("Retrieved {} users", users.size());

        } catch (SQLException e) {
            logger.error("Error retrieving users", e);
        }
        return users;
    }

    @Override
    public List<User> getAll() {
        return findAll();
    }
}
