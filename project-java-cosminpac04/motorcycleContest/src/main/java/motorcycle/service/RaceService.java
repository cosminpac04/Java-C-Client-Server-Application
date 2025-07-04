package motorcycle.service;
import java.util.List;
import motorcycle.model.Race;
import motorcycle.repository.RaceHibernateRepository;
import motorcycle.repository.RaceRepository;


import java.util.List;

public class RaceService {
    private final RaceHibernateRepository raceRepository;

    public RaceService(RaceHibernateRepository raceRepository){
        this.raceRepository = raceRepository;
    }

    public void addRace(Race race){
        raceRepository.add(race);
    }

    public void deleteRace(Race race){
        raceRepository.delete(race);
    }

    public void updateRace(Race race, int id){
        raceRepository.update(race, id);
    }

    public Race findRaceById(int id){
        return raceRepository.findById(id);
    }

    public List<Race> getAllRaces(){
        return raceRepository.findAll();
    }
}
