package sample;

import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BibliotekaDAO {

    private static BibliotekaDAO instance;
    private Connection connection;
    private PreparedStatement addUserQuery, addAdminQuery, findAdminQuery, findUserQuery, adminsQuery, usersQuery;

    private BibliotekaDAO(){

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            addUserQuery = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            regenerateDB();
            try {
                addUserQuery = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?, ?)");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            addAdminQuery = connection.prepareStatement("INSERT INTO admin VALUES (?, ?, ?, ?, ?)");
            findAdminQuery = connection.prepareStatement("SELECT * FROM admin WHERE username = ?");
            findUserQuery = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            adminsQuery = connection.prepareStatement("SELECT * FROM admin");
            usersQuery = connection.prepareStatement("SELECT * FROM user");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static BibliotekaDAO getInstance() {
        if (instance == null) instance = new BibliotekaDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerateDB() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User user){

        ArrayList<Administrator> administrators = new ArrayList<>();
        boolean imaLi = false;

        administrators.addAll(admins());


        for (Administrator a : administrators){
            if(a.getUsername().equals(user.getUsername())){
                imaLi = true;
            }
        }

        try {

            if(imaLi){
                throw new SQLException();
            }

            addUserQuery = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?, ?)");

            addUserQuery.setString(1, user.getUsername());
            addUserQuery.setString(2, user.getName());
            addUserQuery.setString(3, user.getLastName());
            addUserQuery.setString(4, user.getEmail());
            addUserQuery.setString(5, user.getPassword());
            addUserQuery.executeUpdate();
            return true;

        } catch (SQLException e) {
            alreadyExists(user.getUsername());
            return false;
        }
    }

    public void addAdmin(Administrator administrator){


        ArrayList<User> users = new ArrayList<>();
        boolean imaLi = false;

        users.addAll(users());


        for (User a : users){
            if(a.getUsername().equals(administrator.getUsername())){
                imaLi = true;
            }
        }

        try {

            if(imaLi){
                throw new SQLException();
            }

            addAdminQuery.setString(1, administrator.getUsername());
            addAdminQuery.setString(2, administrator.getName());
            addAdminQuery.setString(3, administrator.getLastName());
            addAdminQuery.setString(4, administrator.getEmail());
            addAdminQuery.setString(5, administrator.getPassword());

            addAdminQuery.executeUpdate();
        } catch (SQLException e) {
            alreadyExists(administrator.getUsername());
        }
    }

    public Administrator getAdministratorFromResultSet(ResultSet resultSet){
        Administrator user = null;
        try {
            user = new Administrator(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserFromResultSet(ResultSet resultSet){
        User user = null;
        try {
            user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Person find(Person person){

        ArrayList<Administrator> admins = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        admins.addAll(admins());
        users.addAll(users());

        for (Administrator a : admins){
            if(a.getUsername().equals(person.getUsername()) && a.getPassword().equals(person.getPassword())){
                return new Administrator(a.getUsername(), a.getName(), a.getLastName(), a.getEmail(), a.getPassword());
            }
        }

        for (User a : users){
            if(a.getUsername().equals(person.getUsername()) && a.getPassword().equals(person.getPassword())){
                return new User(a.getUsername(), a.getName(), a.getLastName(), a.getEmail(), a.getPassword());
            }
        }

        return null;
    }

    public ArrayList<Administrator> admins(){

        ArrayList<Administrator> admins = new ArrayList<>();

        try {
            ResultSet rs = adminsQuery.executeQuery();
            while(rs.next()){
                admins.add(getAdministratorFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admins;
    }

    public ArrayList<User> users(){

        ArrayList<User> users = new ArrayList<>();

        try {
            ResultSet rs = usersQuery.executeQuery();
            while(rs.next()){
                users.add(getUserFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void alreadyExists(String username){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška");
        alert.setHeaderText("Korisnik sa tim korisničkim imenom " + username + " već postoji");
        alert.setContentText("Pokušajte ponovo");
        alert.setResizable(true);
        alert.show();
    }

}
