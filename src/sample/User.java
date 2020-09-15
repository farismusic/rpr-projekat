package sample;

import java.io.Serializable;

public class User extends Person implements Serializable {

    public User() {
    }

    public User(String username, String name, String lastName, String email, String password) {
        super(username, name, lastName, email, password);
    }

    @Override
    public String toString() {
        return super.getUsername() + " " + super.getName() + " " + super.getLastName() + " " + super.getEmail();
    }
}
