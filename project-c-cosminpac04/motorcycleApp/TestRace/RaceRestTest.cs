using System;
using System.Net.Http;
using System.Net.Http.Json;
using System.Text.Json;
using System.Threading.Tasks;

namespace MotorcycleServer.TestRest;
public class RaceRestTest {
    private static readonly HttpClient client = new HttpClient();
    private const string BaseUrl = "http://localhost:8080/api/races";

    public static async Task Main()
    {
        //GET
        var racesJson = await client.GetStringAsync(BaseUrl);
        Console.WriteLine("GET Response:\n" + racesJson);

        // POST
        var newRace = new
        {
            capacity = 500,

        };

        var response = await client.PostAsJsonAsync(BaseUrl, newRace);
        var createdRace = await response.Content.ReadAsStringAsync();
        Console.WriteLine("POST Response:\n" + createdRace);

        HttpResponseMessage deleteResponse = await client.DeleteAsync($"{BaseUrl}/3");
        Console.WriteLine("DELETE Status Code: " + deleteResponse.StatusCode);

    }
}