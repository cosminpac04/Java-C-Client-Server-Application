using System;
using System.IO;
using System.Net.Sockets;

namespace motorcycleAppLibrary
{
    public class ClientNotifier
    {
        private readonly TcpClient _client;

        public ClientNotifier(TcpClient client)
        {
            _client = client;
        }

        public void SendUpdateRequest()
        {
            try
            {
                StreamWriter writer = new(_client.GetStream()) { AutoFlush = true };
                writer.WriteLine("REQUEST_UPDATE");
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error sending update: " + ex.Message);
            }
        }
    }
}