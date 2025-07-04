/*using System.Collections.Generic;
using motorcycleApp.Models;
using motorcycleApp.Repositories;


namespace motorcycleApp.Services
{
    public class ParticipantService
    {
        private readonly ParticipantRepository participantRepo;

        public ParticipantService(ParticipantRepository participantRepository)
        {
            participantRepo = participantRepository;
        }

        public void AddParticipant(Participant participant)
        {
            participantRepo.Add(participant);
        }


        public void RemoveParticipant(Participant participant)
        {
            participantRepo.Delete(participant);
        }

        public void UpdateParticipant(Participant participant)
        {
            participantRepo.Update(participant);
        }

        public Participant GetParticipantById(int id)
        {
            return participantRepo.FindById(id);
        }

        public IEnumerable<Participant> GetAllParticipants()
        {
            return participantRepo.GetAll();
        }
    }
}*/