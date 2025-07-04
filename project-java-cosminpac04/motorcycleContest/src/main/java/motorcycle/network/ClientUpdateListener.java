package motorcycle.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import motorcycle.model.Participant;

public class ClientUpdateListener {

    private static List<Participant> participants = new ArrayList<>();
    private static Runnable uiUpdateCallback;

    public static void startListener(Socket socket, Runnable onUpdateCallback) {
        uiUpdateCallback = onUpdateCallback;
        new Thread(() -> {
            try {
                // Request participants immediately on connect
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println("GET_PARTICIPANTS");
                System.out.println("Requesting initial participants from server");
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                List<Participant> tempParticipants = new ArrayList<>();
                boolean receivingParticipants = false;
                
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received from server: " + message);
                    
                    if (message.equals("UPDATE")) {
                        System.out.println("Received UPDATE message from server");
                        // When we get an update message, request the updated participants
                        tempParticipants = new ArrayList<>(); // Clear temporary list
                        writer.println("GET_PARTICIPANTS");
                    } else if (message.equals("END")) {
                        if (receivingParticipants) {
                            // We finished receiving participants
                            receivingParticipants = false;
                            participants = new ArrayList<>(tempParticipants); // Create a new copy
                            updateUI();
                            System.out.println("Finished receiving " + participants.size() + " participants");
                        }
                    } else if (message.startsWith("GET_PARTICIPANTS")) {
                        // Ignoring this echo of our command
                        System.out.println("Ignoring echo of our command");
                    } else {
                        // This should be participant data
                        receivingParticipants = true;
                        String[] parts = message.split("\\|");
                        if (parts.length == 4) {
                            try {
                                int id = Integer.parseInt(parts[0]);
                                String name = parts[1];
                                int engineCapacity = Integer.parseInt(parts[2]);
                                String team = parts[3];
                                
                                tempParticipants.add(new Participant(id, name, engineCapacity, team));
                                System.out.println("Added participant to temp list: " + name);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid participant data: " + message);
                            }
                        } else {
                            System.err.println("Invalid participant format: " + message);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error in client listener: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    
    private static void updateUI() {
        if (uiUpdateCallback != null) {
            Platform.runLater(uiUpdateCallback);
        }
    }
    
    public static List<Participant> getParticipants() {
        return new ArrayList<>(participants); // Return a copy to prevent concurrent modification
    }
}
