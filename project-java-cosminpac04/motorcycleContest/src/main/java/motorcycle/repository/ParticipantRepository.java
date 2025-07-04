package motorcycle.repository;

import motorcycle.database.DataBaseConnection;
import motorcycle.model.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantRepository implements IRepository<Participant, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(ParticipantRepository.class);

    @Override
    public void add(Participant participant) {
        String sql = "INSERT INTO Participant (Name, EngineCapacity, Team) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participant.getName());
            stmt.setInt(2, participant.getEngineCapacity());
            stmt.setString(3, participant.getTeam());
            stmt.executeUpdate();

            logger.info("Added participant: {}", participant.getName());

        } catch (SQLException e) {
            logger.error("Error adding participant: {}", participant.getName(), e);
        }
    }

    @Override
    public void delete(Participant participant) {
        String sql = "DELETE FROM Participant WHERE Pid = ?";
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, participant.getID());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Deleted participant with ID: {}", participant.getID());
            } else {
                logger.warn("No participant found with ID: {}", participant.getID());
            }

        } catch (SQLException e) {
            logger.error("Error deleting participant with ID: {}", participant.getID(), e);
        }
    }

    @Override
    public void update(Participant participant, Integer id) {
        String sql = "UPDATE Participant SET Name = ?, EngineCapacity = ?, Team = ? WHERE Pid = ?";
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participant.getName());
            stmt.setInt(2, participant.getEngineCapacity());
            stmt.setString(3, participant.getTeam());
            stmt.setInt(4, id);
            stmt.executeUpdate();

            logger.info("Updated participant with ID: {}", id);

        } catch (SQLException e) {
            logger.error("Error updating participant with ID: {}", id, e);
        }
    }

    @Override
    public Participant findById(Integer Pid) {
        String sql = "SELECT * FROM Participant WHERE Pid = ?";
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Pid);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Participant participant = new Participant(
                        rs.getInt("Pid"),
                        rs.getString("name"),
                        rs.getInt("EngineCapacity"),
                        rs.getString("team")
                );
                logger.info("Found participant: {}", participant.getName());
                return participant;
            } else {
                logger.warn("No participant found with ID: {}", Pid);
            }

        } catch (SQLException e) {
            logger.error("Error finding participant with ID: {}", Pid, e);
        }
        return null;
    }

    @Override
    public List<Participant> findAll() {
        List<Participant> participants = new ArrayList<>();
        String sql = "SELECT * FROM Participant";

        try (Connection conn = DataBaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                participants.add(new Participant(
                        rs.getInt("Pid"),
                        rs.getString("Name"),
                        rs.getInt("EngineCapacity"),
                        rs.getString("Team")
                ));
            }

            logger.info("Retrieved {} participants", participants.size());

        } catch (SQLException e) {
            logger.error("Error retrieving participants", e);
        }
        return participants;
    }

    @Override
    public List<Participant> getAll() {
        return findAll();
    }
}
