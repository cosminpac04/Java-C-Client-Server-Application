using System;
using System.Data;
using System.Data.SQLite;
using System.IO;
using Serilog;

namespace motorcycleApp.DataBase;
public static class ConnectionManager
{
    private static readonly string ConnectionString =
        @"Data source=D:\Facultate\An 2 sem2\Mpp\Proiect c#\project-c-cosminpac04\motorcycleApp\DataBase\MotorcycleApp1.sqlite;Version=3";

    public static SQLiteConnection GetConnection()
    {
        try
        {
            var connection = new SQLiteConnection(ConnectionString);
            connection.Open();
            Log.Information("Connection established");
            return connection;
        }
        catch (Exception e)
        {
            Log.Error(e, "Error opening connection");
            throw;
        }
    }

    public static void CloseConnection(SQLiteConnection connection)
    {
        if (connection != null && connection.State == ConnectionState.Open)
        {
            try
            {
                connection.Close();
                Log.Information("Connection closed");
            }
            catch (Exception e)
            {
                Log.Error(e, "Error closing connection");
            }
        }
    }
    public static void TestConnection()
    {
        try
        {
            using (var conn = GetConnection())
            {
                using (var cmd = new SQLiteCommand("SELECT 1", conn))
                {
                    
                    var result = cmd.ExecuteScalar();
                    Console.WriteLine(result != null ? " Connection is working!" : " Connection test failed.");
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($" Connection failed: {ex.Message}");
        }
    }
}