package motorcycle.model;

import java.io.Serializable;

public class User  implements Identifiable<Integer>, Serializable {
    private Integer ID;
    private String username;
    private String password;
    private String role;
    //constructor
    public User(Integer ID, String username, String password, String role){
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole(){
        return role;
    }
    @Override
    public Integer getID() {
        return ID;
    }

    //setters
    public void setRole(String role){
        this.role = role;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }
}

