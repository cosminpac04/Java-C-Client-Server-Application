using System;
using System.Collections.Generic;
using System.Data.SQLite;
using motorcycleApp.DataBase;
using motorcycleApp.Models;
using Serilog;

namespace motorcycleApp.Repositories;

public class RaceRepository : IRepository<Race, int> {
    public void Add(Race entity) {
        string sql = "INSERT INTO Race (Capacity) VALUES (@Capacity)";
        try {
            using (var conn = ConnectionManager.GetConnection()) {
                Log.Information("Connection established " + conn.Database);
                
                using (var command = new SQLiteCommand(sql, conn)) {
                    command.Parameters.AddWithValue("@Capacity", entity.Capacity);
                    command.ExecuteNonQuery();
                    Log.Information("Added race with capacity " + entity.Capacity + " to Database");
                }
            }
        }
        catch (Exception ex) {
            Log.Error(ex, "Error while adding race with capacity " + entity.Capacity + " to Database");
        }
    }

    public void Update(Race entity) {
        string sql = "UPDATE Race SET Capacity=@Capacity WHERE ID=@Id";

        using (var conn = ConnectionManager.GetConnection()) {
            Log.Information("Connection established " + conn.Database);
            
            using (var command = new SQLiteCommand(sql, conn)) {
                command.Parameters.AddWithValue("@Id", entity.ID);
                command.Parameters.AddWithValue("@Capacity", entity.Capacity);
                command.ExecuteNonQuery();
                Log.Information("Updated race with ID " + entity.ID + " to capacity " + entity.Capacity);
            }
        }
    }

    public void Delete(Race entity) {
        string sql = "DELETE FROM Race WHERE ID=@Id";

        using (var conn = ConnectionManager.GetConnection()) {
            Log.Information("Connection established " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn)) {
                command.Parameters.AddWithValue("@Id", entity.ID);
                int affectedRows = command.ExecuteNonQuery();
                
                if (affectedRows > 0) {
                    Log.Information("Deleted race with ID " + entity.ID + " from Database");
                }
                else {
                    Log.Warning("Attempted to delete race with ID " + entity.ID + " but no rows were affected");
                }
            }
        }
    }

    public Race FindById(int id) {
        string sql = "SELECT * FROM Race WHERE ID=@Id";

        using (var conn = ConnectionManager.GetConnection()) {
            Log.Information("Connection established " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn)) {
                command.Parameters.AddWithValue("@Id", id);
                using (var reader = command.ExecuteReader()) {
                    if (reader.Read()) {
                        var race = new Race(
                            reader.GetInt32(0), // ID
                            reader.GetInt32(1)  // Capacity
                        );
                        Log.Information("Found race with ID " + id);
                        return race;
                    }
                }
            }
        }
        Log.Warning("Could not find race with ID " + id + " in Database");
        return null;
    }

    public IEnumerable<Race> GetAll() {
        string sql = "SELECT * FROM Race";
        var races = new List<Race>();

        using (var conn = ConnectionManager.GetConnection()) {
            Log.Information("Connection established " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn))
            using (var reader = command.ExecuteReader()) {
                while (reader.Read()) {
                    races.Add(new Race(
                        reader.GetInt32(0), // ID
                        reader.GetInt32(1)  // Capacity
                    ));
                }
            }
        }
        Log.Information("Found " + races.Count + " races in Database");
        return races;
    }
}
