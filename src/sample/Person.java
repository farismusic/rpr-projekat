package sample;

public class Person {

    private String username, name, lastName, email, password;


    public Person() {
    }

    public Person(String username, String name, String lastName, String email, String password) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public Person setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Person setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Person setPassword(String password) {
        this.password = password;
        return this;
    }
}
