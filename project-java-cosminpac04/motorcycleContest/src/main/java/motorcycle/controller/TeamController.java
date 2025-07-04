//package controller;
//import model.Team;
//import service.TeamService;
//import java.util.List;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/teams")
//public class TeamController {
//    private final TeamService teamService;
//
//    public TeamController(TeamService teamService) {
//        this.teamService = teamService;
//    }
//
//    @PostMapping
//    public void addTeam(@RequestBody Team team) {
//        teamService.addTeam(team);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteTeam(@PathVariable int id) {
//        teamService.removeTeam(teamService.findTeamById(id));
//    }
//
//    @PutMapping("/{id}")
//    public void updateTeam(@RequestBody Team team, @PathVariable int id) {
//        teamService.updateTeam(team, id);
//    }
//
//    @GetMapping("/{id}")
//    public Team findTeamById(@PathVariable int id) {
//        return teamService.findTeamById(id);
//    }
//
//    @GetMapping("/{id}")
//    public List<Team> getAllTeams() {
//        return teamService.findAllTeams();
//    }
//}
