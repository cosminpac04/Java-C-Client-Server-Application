package motorcycle.repository;

import motorcycle.database.DataBaseConnection;
import motorcycle.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository implements IRepository<Team, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(TeamRepository.class);

    @Override
    public void add(Team team) {
            String sql = "INSERT INTO teams (TeamName) VALUES (?)";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, team.getTeamName());
            stmt.executeUpdate();

            logger.info("Added team: {}", team.getTeamName());

        } catch (SQLException e) {
            logger.error("Error adding team: {}", team.getTeamName(), e);
        }
    }

    @Override
    public void delete(Team team) {
        String sql = "DELETE FROM teams WHERE Tid = ?";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, team.getID());
            stmt.executeUpdate();

            logger.info("Deleted team with ID: {}", team.getID());

        } catch (SQLException e) {
            logger.error("Error deleting team with ID: {}", team.getID(), e);
        }
    }

    @Override
    public void update(Team team, Integer id) {
        String sql = "UPDATE teams SET TeamName = ? WHERE Tid = ?";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, team.getTeamName());
            stmt.setInt(2, id);
            stmt.executeUpdate();

            logger.info("Updated team with ID: {}", id);

        } catch (SQLException e) {
            logger.error("Error updating team with ID: {}", id, e);
        }
    }

    @Override
    public Team findById(Integer id) {
        String sql = "SELECT * FROM teams WHERE Tid = ?";
        try (Connection conn = (Connection) DataBaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Team team = new Team(rs.getInt("id"), rs.getString("team_name"));
                logger.info("Found team: {}", team.getTeamName());
                return team;
            } else {
                logger.warn("No team found with ID: {}", id);
            }

        } catch (SQLException e) {
            logger.error("Error finding team with ID: {}", id, e);
        }
        return null;
    }

    @Override
    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams";

        try (Connection conn = (Connection) DataBaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                teams.add(new Team(rs.getInt("id"), rs.getString("team_name")));
            }

            logger.info("Retrieved {} teams", teams.size());

        } catch (SQLException e) {
            logger.error("Error retrieving teams", e);
        }
        return teams;
    }

    @Override
    public List<Team> getAll() {
        return findAll();
    }
}
