package motorcycle.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Race")
@XmlRootElement
public class Race implements Identifiable<Integer>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Rid")
    private int ID;

    @Column(name = "Capacity")
    private int capacity;

    @Transient
    private List<Participant> participants;

    public Race() {
    }

    public Race(int ID, int capacity) {
        this.ID = ID;
        this.capacity = capacity;
    }

    // Getters and setters

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Participant> getParticipants() {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }
}