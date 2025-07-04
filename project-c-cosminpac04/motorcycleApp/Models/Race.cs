using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace motorcycleApp.Models
{
    public class Race : Identifiable<int>
    {
        [Key]
        public int ID { get; set; }
    
        public int Capacity { get; set; }

        [System.Text.Json.Serialization.JsonIgnore]
        public List<Participant> Participants { get; set; } = new List<Participant>();

        public Race() {}

        public Race(int id, int capacity)
        {
            ID = id;
            Capacity = capacity;
        }
    }

}