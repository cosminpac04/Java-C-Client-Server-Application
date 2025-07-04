using System.ComponentModel.DataAnnotations;

namespace motorcycleApp.Models
{
    public class Participant : Identifiable<int>
    {
        [Key] public int ID { get; set; }

        public string Name { get; set; } = string.Empty;

        public int EngineCapacity { get; set; }

        public string Team { get; set; } = string.Empty;

        public Participant()
        {
            
        }

        public Participant(int id, string name, int engineCapacity, string team)
        {
            ID = id;
            Name = name;
            EngineCapacity = engineCapacity;
            Team = team;
        }
        
        
        
        public override string ToString()
        {
            return $"Participant: {Name} ({EngineCapacity}cc, Team: {Team})";
        }
    }
}
