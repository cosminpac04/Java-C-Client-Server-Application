package motorcycle.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import motorcycle.model.Participant;
import motorcycle.network.ClientNotifier;
import motorcycle.network.ClientUpdateListener;
import motorcycle.repository.ParticipantHibernateRepository;
import motorcycle.service.ParticipantHibernateService;
import motorcycle.util.HibernateUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class MainController {

    @FXML private TableView<Participant> contestTable;
    @FXML private TableColumn<Participant, Integer> colId;
    @FXML private TableColumn<Participant, String> colRider;
    @FXML private TableColumn<Participant, Integer> colEngineCapacity;
    @FXML private TableColumn<Participant, String> colTeam;

    @FXML private TextField nameField;
    @FXML private TextField engineCapacityField;
    @FXML private TextField teamField;

    private ObservableList<Participant> participants = FXCollections.observableArrayList();

    private final ParticipantHibernateService participantService;

    public MainController() {
        ParticipantHibernateRepository repository = new ParticipantHibernateRepository(HibernateUtil.getSessionFactory());
        this.participantService = new ParticipantHibernateService(repository);
    }

    private Socket socket;

    public void setSocket(Socket socket) {
        if (socket != null) {
            this.socket = socket;
            ClientUpdateListener.startListener(socket, this::updateParticipantsFromListener);
        } else {
            System.out.println("Error: Socket is null!");
        }
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colRider.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEngineCapacity.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));
        colTeam.setCellValueFactory(new PropertyValueFactory<>("team"));

        contestTable.setItems(participants);
        if (socket != null) {
            ClientUpdateListener.startListener(socket, this::updateParticipantsFromListener);
        } else {
            showAlert("Error", "Not connected to server");
        }
    }

    private void updateParticipantsFromListener() {
        List<Participant> receivedParticipants = ClientUpdateListener.getParticipants();
        System.out.println("Updating UI with " + receivedParticipants.size() + " participants");
        
        Platform.runLater(() -> {
            participants.clear();
            participants.addAll(receivedParticipants);
            contestTable.refresh();
            System.out.println("UI updated with " + participants.size() + " participants");
        });
    }

    @FXML
    private void onAddParticipant() {
        String name = nameField.getText();
        String engineCapacityStr = engineCapacityField.getText();
        String team = teamField.getText();

        if (name.isEmpty() || engineCapacityStr.isEmpty() || team.isEmpty()) {
            showAlert("Error", "All fields must be filled out");
            return;
        }

        try {
            int engineCapacity = Integer.parseInt(engineCapacityStr);
            // Send to server instead of adding locally
            if (socket != null && socket.isConnected()) {
                try {
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    String command = "ADD_PARTICIPANT|" + name + "|" + engineCapacity + "|" + team;
                    System.out.println("Sending to server: " + command);
                    writer.println(command);
                    
                    // Clear the fields
                    nameField.clear();
                    engineCapacityField.clear();
                    teamField.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to send data to server: " + e.getMessage());
                }
            } else {
                showAlert("Error", "Not connected to server");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Engine Capacity must be a number");
        }
    }

    @FXML
    private void onDeleteParticipant() {
        Participant selectedParticipant = contestTable.getSelectionModel().getSelectedItem();

        if (selectedParticipant == null) {
            showAlert("Error", "No participant selected");
            return;
        }

        if (socket != null && socket.isConnected()) {
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                String command = "DELETE_PARTICIPANT|" + selectedParticipant.getID();
                System.out.println("Sending to server: " + command);
                writer.println(command);
                
                // The server will send an update notification that will trigger a refresh
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to send delete request: " + e.getMessage());
            }
        } else {
            showAlert("Error", "Not connected to server");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

