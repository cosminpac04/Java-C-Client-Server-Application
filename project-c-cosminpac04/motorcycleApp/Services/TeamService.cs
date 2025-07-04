using System.Collections.Generic;
using motorcycleApp.Models;
using motorcycleApp.Repositories;

namespace motorcycleApp.Services
{
    public class TeamService
    {
        private readonly TeamRepository _teamRepository;

        public TeamService(TeamRepository teamRepository)
        {
            _teamRepository = teamRepository;
        }

        public void AddTeam(Team team)
        {
            _teamRepository.Add(team);
        }

        public void RemoveTeam(int id)
        {
            var team = _teamRepository.FindById(id);
            _teamRepository.Delete(team);
        }

        public void UpdateTeam(int id, Team team)
        {
            _teamRepository.Update(team);
        }

        public Team GetTeamById(int id)
        {
            return _teamRepository.FindById(id);
        }

        public IEnumerable<Team> GetAllTeams()
        {
            return _teamRepository.GetAll();
        }
    }
}