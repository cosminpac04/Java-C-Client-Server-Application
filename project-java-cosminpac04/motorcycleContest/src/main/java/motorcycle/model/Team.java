package motorcycle.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Team  implements Identifiable<Integer>, Serializable {
    private int ID;
    private String teamName;
    private List<Participant> teamMembers;

    //constructor
    public Team(int ID,String teamName){
        this.ID=ID;
        this.teamName = teamName;
        this.teamMembers = new ArrayList<>();
    }

    //getters
    public String getTeamName() {
        return teamName;
    }
    public List<Participant> getTeamMembers() {
        return teamMembers;
    }
    @Override
    public Integer getID() {
        return ID;
    }

    //setters
    public void setTeamMembers(List<Participant> teamMembers) {
        this.teamMembers = teamMembers;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }
}
