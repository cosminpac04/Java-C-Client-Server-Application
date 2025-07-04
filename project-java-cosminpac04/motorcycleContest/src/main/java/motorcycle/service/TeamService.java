package motorcycle.service;
import java.util.List;
import motorcycle.model.Team;
import motorcycle.repository.TeamRepository;

public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void addTeam(Team team) {
        teamRepository.add(team);
    }

    public void removeTeam(Team team) {
        teamRepository.delete(team);
    }

    public void updateTeam(Team team, int id){
        teamRepository.update(team, id);
    }

    public Team findTeamById(int id){
        return teamRepository.findById(id);
    }

    public List<Team> findAllTeams(){
        return teamRepository.findAll();
    }

}
