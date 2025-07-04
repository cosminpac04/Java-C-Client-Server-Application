//package controller;
//
//import model.Race;
//import service.RaceService;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/races")
//public class RaceController {
//    private final RaceService raceService;
//
//    public RaceController(RaceService raceService) {
//        this.raceService = raceService;
//    }
//
//    @PostMapping
//    public void addRace(@RequestBody Race race) {
//        raceService.addRace(race);
//    }
//
//    @DeleteMapping("/{id}")
//    public void removeRace(@PathVariable int id) {
//        raceService.removeRace(raceService.findRaceById(id));
//    }
//
//    @PutMapping("/{id}")
//    public void updateRace(@RequestBody Race race, @PathVariable int id) {
//        raceService.updateRace(race, id);
//    }
//
//    @GetMapping("/{id}")
//    public Race getRaceById(@PathVariable int id) {
//        return raceService.findRaceById(id);
//    }
//
//    @GetMapping
//    public List<Race> getAllRaces() {
//        return raceService.findAllRaces();
//    }
//}