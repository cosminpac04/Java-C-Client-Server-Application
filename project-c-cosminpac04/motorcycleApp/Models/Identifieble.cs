namespace motorcycleApp.Models
{
	public interface Identifiable<T>
	{
		T ID { get; set; }
	}
}