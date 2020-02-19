package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class BibliotekaDAO {

    private static BibliotekaDAO instance;
    private Connection connection;
    private PreparedStatement addUserQuery, addAdminQuery;

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

    public void addAdmin(User user){
        try {
            addAdminQuery.setString(1, user.getUsername());
            addAdminQuery.setString(2, user.getName());
            addAdminQuery.setString(3, user.getLastName());
            addAdminQuery.setString(4, user.getEmail());
            addAdminQuery.setString(5, user.getPassword());

            addAdminQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
