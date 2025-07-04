//package controller;
//import service.UserService;
//import model.User;
//import java.util.List;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService= userService;
//    }
//
//    @PostMapping
//    public void addUser(@RequestBody User user) {
//        userService.addUser(user);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable int id) {
//        userService.removeUser(userService.findUserById(id));
//    }
//
//    @PutMapping("/{id}")
//    public void updateUser(@RequestBody User user, @PathVariable int id){
//        userService.updateUser(user, id);
//    }
//
//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable int id) {
//        return userService.findUserById(id);
//    }
//
//    @GetMapping("/{id}")
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//}
