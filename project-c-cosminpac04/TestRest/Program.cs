using System.Net.Http.Json;

class Program {

    private static readonly HttpClient client = new HttpClient();
    private const string BaseUrl = "http://localhost:8080/api/races";
    static async Task Main() {
        //GET
        var racesJson = await client.GetStringAsync(BaseUrl);
        Console.WriteLine("GET Response:\n" + racesJson);

        // POST 
        var newRace = new
        {
            engineCapacity = 500,
            maxParticipants = 10,
            currentParticipants = 0
        };

        var response = await client.PostAsJsonAsync(BaseUrl, newRace);
        var createdRace = await response.Content.ReadAsStringAsync();
        Console.WriteLine("POST Response:\n" + createdRace);

        HttpResponseMessage deleteResponse = await client.DeleteAsync($"{BaseUrl}/3");
        Console.WriteLine("DELETE Status Code: " + deleteResponse.StatusCode);

    }
}