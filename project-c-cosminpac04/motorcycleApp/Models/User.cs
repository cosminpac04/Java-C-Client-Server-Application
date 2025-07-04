using System.ComponentModel.DataAnnotations;

namespace motorcycleApp.Models
{
    public class User : Identifiable<int>
    {
        [Key]
        public int ID { get; set; }
        
        public string Username { get; set; }
        
        public string Password { get; set; }
        
        public string Role { get; set; }
		
		public User(){}

        public User(int id, string username, string password, string role)
        {
            ID = id;
            Username = username;
            Password = password;
            Role = role;
        }
    }
}
