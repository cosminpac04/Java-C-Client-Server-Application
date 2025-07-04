using System;
using System.IO;
using System.Net.Sockets;
using System.Threading;
using System.Windows.Forms;

namespace motorcycleAppLibrary
{
    public static class ClientUpdateListener
    {
        public static void StartListener(TcpClient client, Action onUpdateCallback)
        {
            new Thread(() =>
            {
                try
                {
                    using StreamReader reader = new(client.GetStream());
                    string? line;
                    while ((line = reader.ReadLine()) != null)
                    {
                        if (line == "UPDATE")
                        {
                            Console.WriteLine("Received UPDATE message from server.");
                            Application.OpenForms[0].Invoke(onUpdateCallback); // Run on UI thread
                        }
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Client listener error: " + ex.Message);
                }
            }).Start();
        }
    }
}