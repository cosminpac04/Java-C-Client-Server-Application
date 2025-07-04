package motorcycle.client;

import motorcycle.model.Race;
import java.io.IOException;
import java.util.List;

/**
 * Example class demonstrating how to use the RaceApiClient
 */
public class RaceApiExample {

    public static void main(String[] args) {
        // Create a new RaceApiClient instance
        RaceApiClient client = new RaceApiClient();
        
        try {
            // Test connection
            boolean connected = client.testConnection();
            System.out.println("Connection test: " + (connected ? "SUCCESS" : "FAILED"));
            
            if (!connected) {
                System.out.println("Make sure the REST server is running.");
                return;
            }
            
            // Example 1: Add a new race
            System.out.println("\n=== Adding a new race ===");
            Race newRace = new Race();
            newRace.setCapacity(25);
            int newId = client.addRace(newRace);
            System.out.println("Added new race with ID: " + newId);
            
            // Example 2: Get all races
            System.out.println("\n=== Getting all races ===");
            List<Race> allRaces = client.getAllRaces();
            System.out.println("Found " + allRaces.size() + " races:");
            for (Race race : allRaces) {
                System.out.println("Race ID: " + race.getID() + ", Capacity: " + race.getCapacity());
            }
            
            // Example 3: Get race by ID
            System.out.println("\n=== Getting race by ID ===");
            Race race = client.getRaceById(newId);
            System.out.println("Retrieved race - ID: " + race.getID() + ", Capacity: " + race.getCapacity());
            
            // Example 4: Update race
            System.out.println("\n=== Updating race ===");
            race.setCapacity(30);
            client.updateRace(race.getID(), race);
            System.out.println("Race updated successfully");
            
            // Verify update
            Race updatedRace = client.getRaceById(race.getID());
            System.out.println("Verified race - ID: " + updatedRace.getID() + ", Capacity: " + updatedRace.getCapacity());
            
            // Example 5: Delete race
            System.out.println("\n=== Deleting race ===");
            client.deleteRace(newId);
            System.out.println("Race deleted successfully");
            
            // Final count of races
            List<Race> remainingRaces = client.getAllRaces();
            System.out.println("\nRemaining races count: " + remainingRaces.size());
            
        } catch (IOException | InterruptedException e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 