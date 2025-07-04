package motorcycle.model;
import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "Participant")
public class Participant implements Identifiable<Integer>, Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pid")
    private int ID;
    
    @Column(name = "Name")
    private String name;
    
    @Column(name = "EngineCapacity")
    private int engineCapacity;
    
    @Column(name = "Team")
    private String team;

    //constructor
    public Participant(){
        // Default constructor required by Hibernate
    }
    
    public Participant(int ID,String name, int engineCapacity, String team){
        this.ID = ID;
        this.name = name;
        this.engineCapacity = engineCapacity;
        this.team = team;
    }

    public String toProtocolString() {
        return getID() + "|" + getName() + "|" + getEngineCapacity() + "|" + getTeam();
    }

    //getters
    public String getName() {
        return name;
    }
    public Integer getEngineCapacity() {
        return engineCapacity;
    }
    public String getTeam() {
        return team;
    }
    @Override
    public Integer getID() {
        return ID;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }
    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }
}


