package motorcycle.service;

import motorcycle.model.Participant;
import motorcycle.repository.ParticipantRepository;
import java.util.List;

public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository){

        this.participantRepository = participantRepository;
    }

    public void addParticipant(Participant participant){
        participantRepository.add(participant);
    }

    public void removeParticipant(Participant participant){
        participantRepository.delete(participant);
    }

    public void updateParticipant(Participant participant, int id){
        participantRepository.update(participant, id);
    }

    public Participant findParticipantById(int id){
        return participantRepository.findById(id);
    }

    public List<Participant> getAllParticipants(){
        return participantRepository.findAll();
    }

}
