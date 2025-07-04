using System;
using System.Collections.Generic;
using System.Data.SQLite;
using motorcycleApp.DataBase;
using motorcycleApp.Models;
using Serilog;

namespace motorcycleApp.Repositories;

public class TeamRepository : IRepository<Team, int>
{
    public void Add(Team entity)
    {
        string sql = "INSERT INTO Team (TeamName) VALUES (@Name)";
        try
        {
            using (var conn = ConnectionManager.GetConnection())
            {
                Log.Information("Connection established: " + conn.Database);

                using (var command = new SQLiteCommand(sql, conn))
                {
                    command.Parameters.AddWithValue("@Name", entity.TeamName);
                    command.ExecuteNonQuery();
                    Log.Information("Added team '{TeamName}' to Database", entity.TeamName);
                }
            }
        }
        catch (Exception ex)
        {
            Log.Error(ex, "Error while adding team '{TeamName}' to Database", entity.TeamName);
        }
    }

    public void Update(Team entity)
    {
        string sql = "UPDATE Team SET TeamName=@Name WHERE ID=@Id";

        using (var conn = ConnectionManager.GetConnection())
        {
            Log.Information("Connection established: " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn))
            {
                command.Parameters.AddWithValue("@Id", entity.ID);
                command.Parameters.AddWithValue("@Name", entity.TeamName);
                command.ExecuteNonQuery();
                Log.Information("Updated team ID {TeamId} to name '{TeamName}'", entity.ID, entity.TeamName);
            }
        }
    }

    public void Delete(Team entity)
    {
        string sql = "DELETE FROM Team WHERE ID=@Id";

        using (var conn = ConnectionManager.GetConnection())
        {
            Log.Information("Connection established: " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn))
            {
                command.Parameters.AddWithValue("@Id", entity.ID);
                int affectedRows = command.ExecuteNonQuery();
                if (affectedRows > 0)
                {
                    Log.Information("Deleted team ID {TeamId} from Database", entity.ID);
                }
                else
                {
                    Log.Warning("No team found to delete with ID {TeamId}", entity.ID);
                }
            }
        }
    }

    public Team FindById(int id)
    {
        string sql = "SELECT * FROM Team WHERE ID=@Id";

        using (var conn = ConnectionManager.GetConnection())
        {
            Log.Information("Connection established: " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn))
            {
                command.Parameters.AddWithValue("@Id", id);
                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        var team = new Team(
                            reader.GetInt32(0),   // ID
                            reader.GetString(1)   // TeamName
                        );
                        Log.Information("Found team with ID {TeamId}: '{TeamName}'", team.ID, team.TeamName);
                        return team;
                    }
                }
            }
        }

        Log.Warning("Could not find team with ID {TeamId}", id);
        return null;
    }

    public IEnumerable<Team> GetAll()
    {
        string sql = "SELECT * FROM Team";
        var teams = new List<Team>();

        using (var conn = ConnectionManager.GetConnection())
        {
            Log.Information("Connection established: " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn))
            using (var reader = command.ExecuteReader())
            {
                while (reader.Read())
                {
                    teams.Add(new Team(
                        reader.GetInt32(0),   // ID
                        reader.GetString(1)   // TeamName
                    ));
                }
            }
        }

        Log.Information("Retrieved {Count} teams from Database", teams.Count);
        return teams;
    }
}
