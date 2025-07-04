using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace motorcycleAppLibrary
{
    public static class NotificationServer
    {
        private const int Port = 12345;
        private static readonly HashSet<StreamWriter> Clients = new();

        public static void Start()
        {
            new Thread(() =>
            {
                try
                {
                    TcpListener server = new TcpListener(IPAddress.Any, Port);
                    server.Start();
                    Console.WriteLine($"Server started on port {Port}");

                    while (true)
                    {
                        TcpClient client = server.AcceptTcpClient();
                        Console.WriteLine("Client connected: " + client.Client.RemoteEndPoint);

                        StreamWriter writer = new StreamWriter(client.GetStream()) { AutoFlush = true };
                        lock (Clients)
                        {
                            Clients.Add(writer);
                        }

                        new Thread(() => HandleClient(client, writer)).Start();
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Server error: " + ex.Message);
                }
            }).Start();
        }

        private static void HandleClient(TcpClient client, StreamWriter writer)
        {
            try
            {
                using StreamReader reader = new(client.GetStream());
                string? line;
                while ((line = reader.ReadLine()) != null)
                {
                    if (line == "REQUEST_UPDATE")
                    {
                        Console.WriteLine("Server received update request.");
                        NotifyClients("UPDATE");
                    }
                }
            }
            catch (IOException)
            {
                // Client disconnected
            }
            finally
            {
                lock (Clients)
                {
                    Clients.Remove(writer);
                }

                client.Close();
            }
        }

        public static void NotifyClients(string message)
        {
            Console.WriteLine($"Broadcasting message: {message}");
            lock (Clients)
            {
                foreach (var client in Clients)
                {
                    try
                    {
                        client.WriteLine(message);
                    }
                    catch { /* Ignore failed sends */ }
                }
            }
        }
    }
}
