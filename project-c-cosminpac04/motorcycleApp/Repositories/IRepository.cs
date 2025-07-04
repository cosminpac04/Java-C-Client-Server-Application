using System.Collections.Generic;
using motorcycleApp.Models;

namespace motorcycleApp.Repositories
{
	public interface IRepository<T, Tid>
		where T : Identifiable<Tid>
	{
		void Add(T entity);
		void Delete(T entity);
		void Update(T entity);
		T FindById(Tid id);
		IEnumerable<T> GetAll();
	}
}