// using System;
// using System.Collections.Generic;
// using System.Data.SQLite;
// using motorcycleApp.Models;
//
// namespace motorcycleApp.Repositories
// {
//     public class ParticipantRepository : IRepository<Participant, int>
//     {
//         public void Add(Participant participant)
//         {
//             using (var connection = AppDbContext.GetConnection())
//             {
//                 using (var command = new SQLiteCommand("INSERT INTO Participant (Name, EngineCapacity, Team) VALUES (@Name, @EngineCapacity, @Team)", connection))
//                 {
//                     command.Parameters.AddWithValue("@Name", participant.Name);
//                     command.Parameters.AddWithValue("@EngineCapacity", participant.EngineCapacity);
//                     command.Parameters.AddWithValue("@Team", participant.Team);
//                     command.ExecuteNonQuery();
//                 }
//             }
//         }
//
//         public void Delete(Participant participant)
//         {
//             using (var connection = AppDbContext.GetConnection())
//             {
//                 using (var command = new SQLiteCommand("DELETE FROM Participant WHERE ID = @ID", connection))
//                 {
//                     command.Parameters.AddWithValue("@ID", participant.ID); // Use the participant's ID
//                     command.ExecuteNonQuery();
//                 }
//             }
//         }
//
//         public void Update(Participant participant, int id)
//         {
//             using (var connection = AppDbContext.GetConnection())
//             {
//                 using (var command = new SQLiteCommand("UPDATE Participant SET Name = @Name, EngineCapacity = @EngineCapacity, Team = @Team WHERE ID = @ID", connection))
//                 {
//                     command.Parameters.AddWithValue("@Name", participant.Name);
//                     command.Parameters.AddWithValue("@EngineCapacity", participant.EngineCapacity);
//                     command.Parameters.AddWithValue("@Team", participant.Team);
//                     command.Parameters.AddWithValue("@ID", id);
//                     command.ExecuteNonQuery();
//                 }
//             }
//         }
//
//         public Participant FindById(int id)
//         {
//             using (var connection = AppDbContext.GetConnection())
//             {
//                 using (var command = new SQLiteCommand("SELECT ID, Name, EngineCapacity, Team FROM Participant WHERE ID = @ID", connection))
//                 {
//                     command.Parameters.AddWithValue("@ID", id);
//                     using (var reader = command.ExecuteReader())
//                     {
//                         if (reader.Read())
//                         {
//                             return new Participant(
//                                 reader.GetInt32(0),
//                                 reader.GetString(1),
//                                 reader.GetInt32(2),
//                                 reader.GetString(3)
//                             );
//                         }
//                         throw new KeyNotFoundException($"No Participant found with ID: {id}");
//                     }
//                 }
//             }
//         }
//
//         public IEnumerable<Participant> FindAll()
//         {
//             var participants = new List<Participant>();
//             using (var connection = AppDbContext.GetConnection())
//             {
//                 using (var command = new SQLiteCommand("SELECT ID, Name, EngineCapacity, Team FROM Participant", connection))
//                 using (var reader = command.ExecuteReader())
//                 {
//                     while (reader.Read())
//                     {
//                         participants.Add(new Participant(
//                             reader.GetInt32(0),
//                             reader.GetString(1),
//                             reader.GetInt32(2),
//                             reader.GetString(3)
//                         ));
//                     }
//                 }
//             }
//             return participants;
//         }
//
//         // Implementation of GetAll method as per the IRepository interface
//         public ICollection<Participant> GetAll()
//         {
//             var participants = new List<Participant>();
//             using (var connection = AppDbContext.GetConnection())
//             {
//                 using (var command = new SQLiteCommand("SELECT ID, Name, EngineCapacity, Team FROM Participant", connection))
//                 using (var reader = command.ExecuteReader())
//                 {
//                     while (reader.Read())
//                     {
//                         participants.Add(new Participant(
//                             reader.GetInt32(0),
//                             reader.GetString(1),
//                             reader.GetInt32(2),
//                             reader.GetString(3)
//                         ));
//                     }
//                 }
//             }
//             return participants;
//         }
//     }
// }
/*
using System.Data.SQLite;
using motorcycleApp.DataBase;
using motorcycleApp.Models;
using Serilog;
*/

/*namespace motorcycleApp.Repositories;

public class ParticipantRepository : IRepository<Participant, int> {
    public void Add(Participant entity) {
        string sql = "INSERT INTO Participant (Name, EngineCapacity) VALUES (@Name, @EngineCapacity)";
        try {
            using (var conn = ConnectionManager.GetConnection()) {
                
                Log.Information("Connection established " + conn.Database);

                using (var command = new SQLiteCommand(sql, conn)) {
                    command.Parameters.AddWithValue("@Name", entity.Name);
                    command.Parameters.AddWithValue("@EngineCapacity", entity.EngineCapacity);
                    
                    command.ExecuteNonQuery();
                    Log.Information("Added participant " + entity.Name + " to Database");
                }
            }
        }
        catch (Exception ex) {
            Log.Error(ex, "Error while adding participant " + entity.Name + " to Database");
        }
        
    }

    public void Update(Participant entity) {
        string sql = "UPDATE Participant SET Name=@Name, EngineCapacity=@EngineCapacity WHERE Pid=@Id";

        using (var conn = ConnectionManager.GetConnection()) {
            
            Log.Information("Connection established " + conn.Database);
            using (var command = new SQLiteCommand(sql, conn)) {
                command.Parameters.AddWithValue("@Id", entity.ID);
                command.Parameters.AddWithValue("@Name", entity.Name);
                command.Parameters.AddWithValue("@EngineCapacity", entity.EngineCapacity);
                
                command.ExecuteNonQuery();
            }
        }
        Log.Information("Updated participant " + entity.Name + " to Database");
        
    }

    public void Delete(Participant entity) {
        string sql = "DELETE FROM Participant WHERE Pid=@Id";

        using (var conn = ConnectionManager.GetConnection()) {
            
            Log.Information("Connection established " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn)) {
                command.Parameters.AddWithValue("@Id", entity.ID);
                
                int affectedRows = command.ExecuteNonQuery();
                if (affectedRows > 0) {
                    Log.Information("Deleted participant " + entity.Name + " to Database");
                }
                else {
                    Log.Information("Deleted participant " + entity.Name + " to Database");
                }
            }
        }
    }

    public Participant FindById(int id) {
        string sql = "SELECT * FROM Participant WHERE Pid=@Id";

        using (var conn = ConnectionManager.GetConnection()) {
            
            Log.Information("Connection established " + conn.Database);
            using (var command = new SQLiteCommand(sql, conn)) {
                command.Parameters.AddWithValue("@Id", id);
                using (var reader = command.ExecuteReader()) {
                    if (reader.Read()) {
                        Participant participant = new Participant(
                            reader.GetInt32(0),//id
                            reader.GetString(1),//name
                            reader.GetInt32(2));//engine_cap
                        Log.Information("Found participant " + participant.Name + " to Database");
                        return participant;
                    }
                }
            }
        }
        Log.Warning("Could not find participant " + id + " to Database");
        return null;
    }

    public IEnumerable<Participant> GetAll() {
        string sql = "SELECT * FROM Participant";
        var participants = new List<Participant>();
        
        using (var conn = ConnectionManager.GetConnection()) {
            Log.Information("Connection established " + conn.Database);
            
            using (var command = new SQLiteCommand(sql, conn))
            using (var reader = command.ExecuteReader()) {
                while (reader.Read()) {
                    participants.Add(new Participant(
                        reader.GetInt32(0),//id
                        reader.GetString(1),//name
                        reader.GetInt32(2)
                    ));
                }
            }
        }
        Log.Information("Found " + participants.Count + " participants to Database");
        return participants;
    }
}*/