using System;
using System.IO;
using System.Net.Sockets;
using System.Threading;
using System.Threading.Tasks;

namespace motorcycleApp.network
{
    public class ClientUpdateListener
    {
        private static CancellationTokenSource _cancellationTokenSource;

        public static void StartListener(TcpClient clientSocket, Action onUpdateCallback)
        {
            _cancellationTokenSource = new CancellationTokenSource();
            
            Task.Run(async () =>
            {
                try
                {
                    var reader = new StreamReader(clientSocket.GetStream());
                    var buffer = new char[1024];
                    
                    while (!_cancellationTokenSource.Token.IsCancellationRequested)
                    {
                        if (clientSocket.Available > 0)
                        {
                            var bytesRead = await reader.ReadAsync(buffer, 0, buffer.Length);
                            if (bytesRead == 0) break;

                            var message = new string(buffer, 0, bytesRead);
                            if (message.Contains("UPDATE"))
                            {
                                onUpdateCallback.Invoke();
                            }
                        }
                        await Task.Delay(100);
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Error in listener: {ex.Message}");
                }
            }, _cancellationTokenSource.Token);
        }

        public static void StopListener()
        {
            _cancellationTokenSource?.Cancel();
        }
    }
}