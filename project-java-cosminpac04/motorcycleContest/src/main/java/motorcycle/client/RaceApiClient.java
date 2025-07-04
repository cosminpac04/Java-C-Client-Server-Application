package motorcycle.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import motorcycle.model.Race;

/**
 * Client class for interacting with the Race REST API
 */
public class RaceApiClient {
    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    /**
     * Constructor with default base URL (localhost:8080)
     */
    public RaceApiClient() {
        this("http://localhost:8080/api");
    }
    
    /**
     * Constructor with custom base URL
     * @param baseUrl The base URL of the REST API
     */
    public RaceApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Get all races from the API
     * @return List of Race objects
     * @throws IOException If an I/O error occurs
     * @throws InterruptedException If the operation is interrupted
     */
    public List<Race> getAllRaces() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + "/races"))
                .header("Accept", "application/json")
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
            return Arrays.asList(objectMapper.readValue(response.body(), Race[].class));
        } else {
            throw new IOException("Failed to get races. Status code: " + response.statusCode());
        }
    }
    
    /**
     * Get a race by its ID
     * @param id The ID of the race to retrieve
     * @return The Race object
     * @throws IOException If an I/O error occurs
     * @throws InterruptedException If the operation is interrupted
     */
    public Race getRaceById(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + "/races/" + id))
                .header("Accept", "application/json")
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), Race.class);
        } else {
            throw new IOException("Failed to get race with ID " + id + ". Status code: " + response.statusCode());
        }
    }
    
    /**
     * Add a new race
     * @param race The Race object to add
     * @return The ID of the newly created race
     * @throws IOException If an I/O error occurs
     * @throws InterruptedException If the operation is interrupted
     */
    public int addRace(Race race) throws IOException, InterruptedException {
        String raceJson = objectMapper.writeValueAsString(race);
        
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(raceJson))
                .uri(URI.create(baseUrl + "/races"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 201) {
            // Parse the ID from the response
            String body = response.body();
            return objectMapper.readTree(body).get("id").asInt();
        } else {
            throw new IOException("Failed to add race. Status code: " + response.statusCode() + 
                                 ", Response: " + response.body());
        }
    }
    
    /**
     * Update an existing race
     * @param id The ID of the race to update
     * @param race The updated Race object
     * @throws IOException If an I/O error occurs
     * @throws InterruptedException If the operation is interrupted
     */
    public void updateRace(int id, Race race) throws IOException, InterruptedException {
        String raceJson = objectMapper.writeValueAsString(race);
        
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(raceJson))
                .uri(URI.create(baseUrl + "/races/" + id))
                .header("Content-Type", "application/json")
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new IOException("Failed to update race with ID " + id + 
                                 ". Status code: " + response.statusCode() + 
                                 ", Response: " + response.body());
        }
    }
    
    /**
     * Delete a race by its ID
     * @param id The ID of the race to delete
     * @throws IOException If an I/O error occurs
     * @throws InterruptedException If the operation is interrupted
     */
    public void deleteRace(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(baseUrl + "/races/" + id))
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new IOException("Failed to delete race with ID " + id + 
                                 ". Status code: " + response.statusCode() + 
                                 ", Response: " + response.body());
        }
    }
    
    /**
     * Test if the API is accessible
     * @return true if the API is accessible, false otherwise
     */
    public boolean testConnection() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(baseUrl + "/races/ping"))
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
} 