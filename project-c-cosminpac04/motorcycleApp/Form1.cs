using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Windows.Forms;
using motorcycleApp.Models;
using motorcycleApp.network;

namespace motorcycleApp
{
    public partial class Form1 : Form
    {
        private TcpClient _clientSocket = null!;
        private ClientNotifier _clientNotifier = null!;
        private bool _isConnected = false;

        public Form1()
        {
            InitializeComponent();
            this.FormClosing += Form1_FormClosing;
            this.StartPosition = FormStartPosition.CenterScreen;
            this.Visible = true;
            this.Show();
            
            // Start connection in background
            System.Threading.Tasks.Task.Run(() => ConnectToServer());
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            try
            {
                ClientUpdateListener.StopListener();
                _clientSocket?.Close();
            }
            catch { }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
        }

        private void ConnectToServer()
        {
            try
            {
                _clientSocket = new TcpClient();
                
                // Try to connect with a timeout of 2 seconds
                var result = _clientSocket.BeginConnect("127.0.0.1", 12345, null, null);
                bool success = result.AsyncWaitHandle.WaitOne(TimeSpan.FromSeconds(2));

                if (!success)
                {
                    this.Invoke((MethodInvoker)delegate {
                        MessageBox.Show("Could not connect to server (timeout). Please make sure the server is running on port 12345.");
                        _isConnected = false;
                        EnableControls(false);
                    });
                    return;
                }

                _clientSocket.EndConnect(result);
                _isConnected = true;
                _clientNotifier = new ClientNotifier(_clientSocket);

                // Start the listener for server updates
                ClientUpdateListener.StartListener(_clientSocket, () =>
                {
                    if (this.InvokeRequired)
                    {
                        this.Invoke(new Action(() => LoadParticipants()));
                    }
                    else
                    {
                        LoadParticipants();
                    }
                });

                // Initial load of participants
                this.Invoke((MethodInvoker)delegate {
                    LoadParticipants();
                    EnableControls(true);
                });
            }
            catch (Exception ex)
            {
                this.Invoke((MethodInvoker)delegate {
                    MessageBox.Show($"Error connecting to server: {ex.Message}\nPlease make sure the server is running on port 12345.");
                    _isConnected = false;
                    EnableControls(false);
                });
            }
        }

        private void EnableControls(bool enable)
        {
            if (this.InvokeRequired)
            {
                this.Invoke((MethodInvoker)delegate { EnableControls(enable); });
                return;
            }

            btnAddParticipant.Enabled = enable;
            btnDeleteParticipant.Enabled = enable;
            txtNam.Enabled = enable;
            txtEngineCap.Enabled = enable;
        }

        private void LoadParticipants()
        {
            if (!_isConnected) return;

            try
            {
                SendMessage("GET_PARTICIPANTS");
                var participants = ReceiveParticipants();
                if (dataGridView1.InvokeRequired)
                {
                    dataGridView1.Invoke(new Action(() => dataGridView1.DataSource = participants));
                }
                else
                {
                    dataGridView1.DataSource = participants;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error loading participants: {ex.Message}");
                _isConnected = false;
                EnableControls(false);
            }
        }

        private void SendMessage(string message)
        {
            if (!_isConnected) return;

            try
            {
                var writer = new StreamWriter(_clientSocket.GetStream()) { AutoFlush = true };
                writer.WriteLine(message);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error sending message: {ex.Message}");
                _isConnected = false;
                EnableControls(false);
            }
        }

        private List<Participant> ReceiveParticipants()
        {
            var reader = new StreamReader(_clientSocket.GetStream());
            var participants = new List<Participant>();
            string line;

            while ((line = reader.ReadLine()) != null)
            {
                if (line == "END") break;

                var parts = line.Split('|');
                if (parts.Length == 4)
                {
                    participants.Add(new Participant(
                        int.Parse(parts[0]),
                        parts[1],
                        int.Parse(parts[2]),
                        parts[3]
                    ));
                }
            }

            return participants;
        }

        private void btnAddParticipant_Click(object sender, EventArgs e)
        {
            if (!_isConnected) return;

            try
            {
                string name = txtNam.Text.Trim();
                string team = "DefaultTeam";         
                
                if (string.IsNullOrEmpty(name))
                {
                    MessageBox.Show("Please enter a name for the participant.");
                    return;
                }

                if (!int.TryParse(txtEngineCap.Text.Trim(), out int engineCap))
                {
                    MessageBox.Show("Please enter a valid engine capacity.");
                    return;
                }

                SendMessage($"ADD_PARTICIPANT|{name}|{engineCap}|{team}");
                ClearFields();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error adding participant: {ex.Message}");
            }
        }

        private void btnDeleteParticipant_Click(object sender, EventArgs e)
        {
            if (!_isConnected) return;

            try
            {
                if (dataGridView1.SelectedRows.Count > 0)
                {
                    var selected = dataGridView1.SelectedRows[0].DataBoundItem as Participant;
                    if (selected != null)
                    {
                        SendMessage($"DELETE_PARTICIPANT|{selected.ID}");
                    }
                }
                else
                {
                    MessageBox.Show("Please select a participant to delete.");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error deleting participant: {ex.Message}");
            }
        }
        
        private readonly RaceRestClient _raceRestClient = new RaceRestClient();

        private async void TestRaceApiButton_Click(object sender, EventArgs e)
        {
            try
            {
                if (!int.TryParse(TxtCapacity.Text, out int capacity))
                {
                    MessageBox.Show("Please enter a valid number for capacity");
                    return;
                }

                var race = new Race { Capacity = capacity };

                var id = await _raceRestClient.CreateAsync(race);
            
                if (id > 0)
                {
                    MessageBox.Show("Race added successfully!");
                    TxtCapacity.Clear();
                }
                else
                {
                    MessageBox.Show("Error adding race: No ID returned.");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error: {ex.Message}");
            }
        }
        
        private void ClearFields()
        {
            txtNam.Clear();
            txtEngineCap.Clear();
        }
        
    }
}
