using System;
using System.IO;
using System.Net.Sockets;

namespace motorcycleApp.network
{
    public class ClientNotifier
    {
        private readonly TcpClient _clientSocket;  
        private readonly NetworkStream _networkStream;

        public ClientNotifier(TcpClient clientSocket)
        {
            _clientSocket = clientSocket;
            _networkStream = _clientSocket.GetStream(); 
        }

        public void SendUpdateRequest()
        {
            try
            {
                var writer = new StreamWriter(_networkStream);
                writer.WriteLine("REQUEST_UPDATE");
                writer.Flush();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in sending update request: {ex.Message}");
            }
        }
    }
}