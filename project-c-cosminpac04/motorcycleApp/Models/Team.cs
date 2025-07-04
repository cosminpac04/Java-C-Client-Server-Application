using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace motorcycleApp.Models
{
    public class Team : Identifiable<int>
    {
        [Key]
        public int ID { get; set; }
        
        public string TeamName { get; set; }
        
        public List<Participant> TeamMembers { get; set; } = new List<Participant>();

		public Team(){}

        public Team(int id, string teamName)
        {
            ID = id;
            TeamName = teamName;
            TeamMembers = new List<Participant>();
        }
    }
}
