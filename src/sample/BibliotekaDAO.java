package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BibliotekaDAO {

    private static BibliotekaDAO instance;
    private Connection connection;
    private PreparedStatement addUserQuery;

    public static BibliotekaDAO getInstance() {
        if (instance == null) instance = new BibliotekaDAO();
        return instance;
    }

    private BibliotekaDAO(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            addUserQuery = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



    public void addUser(User user){
        try {
            addUserQuery.setString(1, user.getUsername());
            addUserQuery.setString(2, user.getName());
            addUserQuery.setString(3, user.getLastName());
            addUserQuery.setString(4, user.getEmail());
            addUserQuery.setString(5, user.getPassword());

            addUserQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
