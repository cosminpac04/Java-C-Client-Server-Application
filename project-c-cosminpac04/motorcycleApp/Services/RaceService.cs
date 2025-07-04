using System.Collections.Generic;
using motorcycleApp.Models;
using motorcycleApp.Repositories;

namespace motorcycleApp.Services
{
    public class RaceService
    {
        private readonly RaceRepository _raceRepository;

        public RaceService(RaceRepository raceRepository)
        {
            _raceRepository = raceRepository;
        }

        public void AddRace(Race race)
        {
            _raceRepository.Add(race);
        }

        public void RemoveRace(int id)
        {
            var race = _raceRepository.FindById(id);
            _raceRepository.Delete(race);
        }

        public void UpdateRace(int id, Race race)
        {
            _raceRepository.Update(race);
        }

        public Race GetRaceById(int id)
        {
            return _raceRepository.FindById(id);
        }

        public IEnumerable<Race> GetAllRaces()
        {
            return _raceRepository.GetAll();
        }
    }
}