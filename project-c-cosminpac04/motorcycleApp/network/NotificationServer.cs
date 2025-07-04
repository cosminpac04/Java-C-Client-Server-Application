using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace motorcycleApp.network
{
    public class NotificationServer
    {
        private static readonly List<StreamWriter> Clients = new List<StreamWriter>();

        public static void StartServer()
        {
            try
            {
                var serverSocket = new TcpListener(IPAddress.Any, 12345);
                serverSocket.Start();
                Console.WriteLine("Server started on port 12345");

                while (true)
                {
                    var clientSocket = serverSocket.AcceptTcpClient(); // Accept incoming client connections
                    var outStream = new StreamWriter(clientSocket.GetStream()); // Get output stream for communication
                    Clients.Add(outStream);

                    new Thread(() => HandleClient(clientSocket, outStream)).Start(); // Handle each client in a new thread
                }
            }
            catch (SocketException ex)
            {
                Console.WriteLine($"SocketException: {ex.Message}");
            }
            catch (IOException ex)
            {
                Console.WriteLine($"IOException: {ex.Message}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Unexpected Error: {ex.Message}");
            }
        }

        private static void HandleClient(TcpClient clientSocket, StreamWriter outStream)
        {
            try
            {
                var reader = new StreamReader(clientSocket.GetStream());
                string message;
                while ((message = reader.ReadLine()) != null)
                {
                    if (message == "REQUEST_UPDATE")
                    {
                        Console.WriteLine("Received update request from client.");
                        NotifyClients("UPDATE");
                    }
                }
            }
            catch (IOException ex)
            {
                Console.WriteLine($"IOException: Client disconnected: {ex.Message}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error handling client: {ex.Message}");
            }
            finally
            {
                Clients.Remove(outStream); // Clean up
                outStream?.Close();
                clientSocket?.Close();
                Console.WriteLine("Client disconnected.");
            }
        }


        private static void NotifyClients(string message)
        {
            foreach (var client in Clients)
            {
                try
                {
                    client.WriteLine(message);
                    client.Flush();
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Error sending message to client: {ex.Message}");
                }
            }
        }
    }
}

