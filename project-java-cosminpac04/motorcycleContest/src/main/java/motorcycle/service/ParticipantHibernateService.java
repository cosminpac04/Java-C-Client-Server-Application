package motorcycle.service;

import motorcycle.model.Participant;
import motorcycle.repository.IRepository;
import motorcycle.repository.ParticipantHibernateRepository;

import java.util.List;

public class ParticipantHibernateService {
    private final ParticipantHibernateRepository participantRepository;

    public ParticipantHibernateService(ParticipantHibernateRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public void addParticipant(Participant participant) {
        participantRepository.add(participant);
    }

    public void removeParticipant(Participant participant) {
        participantRepository.delete(participant);
    }

    public void updateParticipant(Participant participant, Integer id) {
        participantRepository.update(participant, id);
    }

    public Participant findParticipantById(Integer id) {
        return participantRepository.findById(id);
    }

    public List<Participant> getAllParticipants() {
        return (List<Participant>) participantRepository.findAll();
    }
} 