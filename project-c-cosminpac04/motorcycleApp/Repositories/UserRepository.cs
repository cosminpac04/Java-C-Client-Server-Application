using System;
using System.Collections.Generic;
using System.Data.SQLite;
using motorcycleApp.DataBase;
using motorcycleApp.Models;
using Serilog;

namespace motorcycleApp.Repositories;

public class UserRepository : IRepository<User, int>
{
    public void Add(User entity)
    {
        string sql = "INSERT INTO User (Username, Password, Role) VALUES (@Username, @Password, @Role)";
        try
        {
            using (var conn = ConnectionManager.GetConnection())
            {
                Log.Information("Connection established: " + conn.Database);

                using (var command = new SQLiteCommand(sql, conn))
                {
                    command.Parameters.AddWithValue("@Username", entity.Username);
                    command.Parameters.AddWithValue("@Password", entity.Password);
                    command.Parameters.AddWithValue("@Role", entity.Role);

                    command.ExecuteNonQuery();
                    Log.Information("Added user '{Username}' to Database", entity.Username);
                }
            }
        }
        catch (Exception ex)
        {
            Log.Error(ex, "Error while adding user '{Username}' to Database", entity.Username);
        }
    }

    public void Update(User entity)
    {
        string sql = "UPDATE User SET Username=@Username, Password=@Password, Role=@Role WHERE ID=@Id";

        using (var conn = ConnectionManager.GetConnection())
        {
            Log.Information("Connection established: " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn))
            {
                command.Parameters.AddWithValue("@Id", entity.ID);
                command.Parameters.AddWithValue("@Username", entity.Username);
                command.Parameters.AddWithValue("@Password", entity.Password);
                command.Parameters.AddWithValue("@Role", entity.Role);

                command.ExecuteNonQuery();
                Log.Information("Updated user ID {UserId} to '{Username}'", entity.ID, entity.Username);
            }
        }
    }

    public void Delete(User entity)
    {
        string sql = "DELETE FROM User WHERE ID=@Id";

        using (var conn = ConnectionManager.GetConnection())
        {
            Log.Information("Connection established: " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn))
            {
                command.Parameters.AddWithValue("@Id", entity.ID);

                int affectedRows = command.ExecuteNonQuery();
                if (affectedRows > 0)
                {
                    Log.Information("Deleted user ID {UserId} from Database", entity.ID);
                }
                else
                {
                    Log.Warning("No user found to delete with ID {UserId}", entity.ID);
                }
            }
        }
    }

    public User FindById(int id)
    {
        string sql = "SELECT * FROM User WHERE ID=@Id";

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
                        var user = new User(
                            reader.GetInt32(0),    // ID
                            reader.GetString(1),   // Username
                            reader.GetString(2),   // Password
                            reader.GetString(3)    // Role
                        );
                        Log.Information("Found user with ID {UserId}: '{Username}'", user.ID, user.Username);
                        return user;
                    }
                }
            }
        }

        Log.Warning("Could not find user with ID {UserId}", id);
        return null;
    }

    public IEnumerable<User> GetAll()
    {
        string sql = "SELECT * FROM User";
        var users = new List<User>();

        using (var conn = ConnectionManager.GetConnection())
        {
            Log.Information("Connection established: " + conn.Database);

            using (var command = new SQLiteCommand(sql, conn))
            using (var reader = command.ExecuteReader())
            {
                while (reader.Read())
                {
                    users.Add(new User(
                        reader.GetInt32(0),
                        reader.GetString(1),
                        reader.GetString(2),
                        reader.GetString(3)
                    ));
                }
            }
        }

        Log.Information("Retrieved {Count} users from Database", users.Count);
        return users;
    }
}
