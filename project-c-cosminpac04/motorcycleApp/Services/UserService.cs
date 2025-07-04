using System.Collections.Generic;
using motorcycleApp.Models;
using motorcycleApp.Repositories;

namespace motorcycleApp.Services
{
    public class UserService
    {
        private readonly UserRepository _userRepository;

        public UserService(UserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        public void AddUser(User user)
        {
            _userRepository.Add(user);
        }

        public void RemoveUser(int id)
        {
            var user = _userRepository.FindById(id);
            _userRepository.Delete(user);
        }

        public void UpdateUser(int id, User user)
        {
            _userRepository.Update(user);
        }

        public User GetUserById(int id)
        {
            return _userRepository.FindById(id);
        }

        public IEnumerable<User> GetAllUsers()
        {
            return _userRepository.GetAll();
        }
    }
}