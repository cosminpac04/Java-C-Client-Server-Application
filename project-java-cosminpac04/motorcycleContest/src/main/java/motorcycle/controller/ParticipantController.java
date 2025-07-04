//package controller;
//import model.Participant;
//import service.ParticipantService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/participants")
//public class ParticipantController {
//    private final ParticipantService participantService;
//
//    public ParticipantController(ParticipantService participantService) {
//        this.participantService = participantService;
//    }
//
//    @PostMapping
//    public void addParticipant(@RequestBody Participant participant) {
//        participantService.addParticipant(participant);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteParticipant(@PathVariable int id) {
//        participantService.removeParticipant(participantService.findParticipantById(id));
//    }
//
//    @PutMapping("/{id}")
//    public void updateParticipant(@RequestBody Participant participant, @PathVariable int id){
//        participantService.updateParticipant(participant, id);
//    }
//
//    @GetMapping("/{id}")
//    public Participant getParticipantById(@PathVariable int id) {
//        return participantService.findParticipantById(id);
//    }
//
//    @GetMapping
//    public List<Participant> getAllParticipants() {
//        return participantService.getAllParticipants();
//    }
//}
