using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Json;
using System.Text.Json;
using System.Threading.Tasks;
using motorcycleApp.Models;

namespace motorcycleApp.network
{
    public class RaceRestClient
    {
        private readonly HttpClient _httpClient;
        private readonly string _baseUrl = "http://localhost:8080/api/races";

        public RaceRestClient()
        {
            _httpClient = new HttpClient();
            // Add default headers
            _httpClient.DefaultRequestHeaders.Accept.Add(
                new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
        }

        public async Task<List<Race>> GetAllAsync()
        {
            try
            {
                var response = await _httpClient.GetAsync(_baseUrl);
                response.EnsureSuccessStatusCode();
                return await response.Content.ReadFromJsonAsync<List<Race>>();
            }
            catch (HttpRequestException ex)
            {
                throw new Exception($"Failed to get races: {ex.Message}", ex);
            }
        }

        public async Task<int> CreateAsync(Race race)
        {
            try
            {
                var response = await _httpClient.PostAsJsonAsync(_baseUrl, race);
                response.EnsureSuccessStatusCode();
            
                var json = await response.Content.ReadAsStringAsync();
                var idObj = JsonSerializer.Deserialize<Dictionary<string, int>>(json);
                return idObj["id"];
            }
            catch (HttpRequestException ex)
            {
                throw new Exception($"Failed to create race: {ex.Message}", ex);
            }
        }
    }
}