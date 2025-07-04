package motorcycle.network;

import motorcycle.model.Participant;
import motorcycle.repository.ParticipantHibernateRepository;
import motorcycle.util.HibernateUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationServer {
    private static final int PORT = 12345;
    private static final List<PrintWriter> clients = new CopyOnWriteArrayList<>();

    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.add(out);

                Thread clientThread = new Thread(() -> handleClient(clientSocket, out));
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void notifyClients(String message) {
        System.out.println("Notifying all clients: " + message);
        for (PrintWriter client : clients) {
            client.println(message);
        }
    }

    private static void handleClient(Socket clientSocket, PrintWriter out) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            ParticipantHibernateRepository participantRepo = new ParticipantHibernateRepository(HibernateUtil.getSessionFactory());
            
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                
                if (inputLine.startsWith("ADD_PARTICIPANT|")) {
                    String[] parts = inputLine.split("\\|");
                    if (parts.length == 4) {
                        String name = parts[1];
                        int engineCapacity = Integer.parseInt(parts[2]);
                        String team = parts[3];

                        Participant newParticipant = new Participant(0, name, engineCapacity, team);
                        participantRepo.add(newParticipant);
                        System.out.println("Added new participant: " + newParticipant.getName());
                        
                        // Notify all clients about the update
                        notifyClients("UPDATE");
                    }
                } else if (inputLine.startsWith("DELETE_PARTICIPANT|")) {
                    String[] parts = inputLine.split("\\|");
                    if (parts.length == 2) {
                        int id = Integer.parseInt(parts[1]);
                        Participant p = participantRepo.findById(id);
                        if (p != null) {
                            System.out.println("Deleting participant: " + p.getName());
                            participantRepo.delete(p);
                            
                            // Notify all clients about the update
                            notifyClients("UPDATE");
                        }
                    }
                } else if (inputLine.equals("GET_PARTICIPANTS")) {
                    List<Participant> participants = (List<Participant>) participantRepo.findAll();
                    System.out.println("Sending " + participants.size() + " participants to client");
                    
                    // First clear any previously sent data
                    for (Participant p : participants) {
                        String participantData = p.getID() + "|" + p.getName() + "|" + p.getEngineCapacity() + "|" + p.getTeam();
                        System.out.println("Sending: " + participantData);
                        out.println(participantData);
                    }
                    out.println("END");
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
            clients.remove(out);
        }
    }
}
