package motorcycle.repository;

import motorcycle.database.DataBaseConnection;
import motorcycle.model.Participant;
import motorcycle.model.Race;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaceRepository implements IRepository<Race, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(RaceRepository.class);

    @Override
    public void add(Race race) {
        String sql = "INSERT INTO races (Capacity) VALUES (?)";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, race.getCapacity());
            stmt.executeUpdate();

            // Retrieve generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                race.setID(rs.getInt(1));
            }

            logger.info("Added race with capacity: {}", race.getCapacity());

        } catch (SQLException e) {
            logger.error("Error adding race", e);
        }
    }

    @Override
    public void delete(Race race) {
        String sql = "DELETE FROM races WHERE Rid = ?";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, race.getID());
            stmt.executeUpdate();

            logger.info("Deleted race with ID: {}", race.getID());

        } catch (SQLException e) {
            logger.error("Error deleting race with ID: {}", race.getID(), e);
        }
    }

    @Override
    public void update(Race race, Integer id) {
        String sql = "UPDATE races SET Capacity = ? WHERE Rid = ?";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, race.getCapacity());
            stmt.setInt(2, id);
            stmt.executeUpdate();

            logger.info("Updated race with ID: {}", id);

        } catch (SQLException e) {
            logger.error("Error updating race with ID: {}", id, e);
        }
    }

    @Override
    public Race findById(Integer id) {
        String sql = "SELECT * FROM races WHERE Rid = ?";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Race race = new Race(rs.getInt("id"), rs.getInt("capacity"));
                race.setParticipants(getParticipantsForRace(id));
                logger.info("Found race with ID: {}", id);
                return race;
            } else {
                logger.warn("No race found with ID: {}", id);
            }

        } catch (SQLException e) {
            logger.error("Error finding race with ID: {}", id, e);
        }
        return null;
    }

    @Override
    public List<Race> findAll() {
        List<Race> races = new ArrayList<>();
        String sql = "SELECT * FROM races";

        try (Connection conn = (Connection) DataBaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Race race = new Race(rs.getInt("id"), rs.getInt("capacity"));
                race.setParticipants(getParticipantsForRace(race.getID()));
                races.add(race);
            }

            logger.info("Retrieved {} races", races.size());

        } catch (SQLException e) {
            logger.error("Error retrieving races", e);
        }
        return races;
    }

    @Override
    public List<Race> getAll() {
        return findAll();
    }

    // Helper method to fetch participants for a given race
    private List<Participant> getParticipantsForRace(int raceId) {
        List<Participant> participants = new ArrayList<>();
        String sql = "SELECT p.Pid, p.Name, p.EngineCapacity, p.Team " +
                "FROM participants p " +
                "JOIN race_participants rp ON p.Pid = rp.participant_id " +
                "WHERE rp.race_id = ?";

        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, raceId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                participants.add(new Participant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("engine_capacity"),
                        rs.getString("team")
                ));
            }

            logger.info("Retrieved {} participants for race ID {}", participants.size(), raceId);

        } catch (SQLException e) {
            logger.error("Error retrieving participants for race ID {}", raceId, e);
        }
        return participants;
    }
}
