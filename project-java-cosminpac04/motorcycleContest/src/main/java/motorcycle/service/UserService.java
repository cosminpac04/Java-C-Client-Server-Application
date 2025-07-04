package motorcycle.service;
import java.util.List;
import motorcycle.model.User;
import motorcycle.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void addUser(User user){
        userRepository.add(user);
    }

    public void removeUser(User user){
        userRepository.delete(user);
    }

    public void updateUser(User user, int id){
        userRepository.update(user, id);
    }

    public User findUserById(int id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
