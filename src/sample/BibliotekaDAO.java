package sample;

import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BibliotekaDAO {

    private static BibliotekaDAO instance;
    private Connection connection;
    private PreparedStatement addUserQuery, addAdminQuery, findAdminQuery, findUserQuery, adminsQuery, usersQuery, nextIdBookQuery, getRentingsQuery, nextIdRentQuery, addBookQuery, booksQuery,
    restBooksQuery, removeBookQuery, removeUserQuery, editBookQuery, addRentQuery;

    private BibliotekaDAO(){

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            addUserQuery = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            regenerateDB();
            try {
                addUserQuery = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?)");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            addAdminQuery = connection.prepareStatement("INSERT INTO admins VALUES (?, ?, ?, ?, ?)");
            findAdminQuery = connection.prepareStatement("SELECT * FROM admins WHERE username = ?");
            findUserQuery = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            adminsQuery = connection.prepareStatement("SELECT * FROM admins");
            usersQuery = connection.prepareStatement("SELECT * FROM users");
            nextIdBookQuery = connection.prepareStatement("SELECT max(id) + 1 FROM books");
            nextIdRentQuery = connection.prepareStatement("SELECT max(id) + 1 FROM rentings");
            getRentingsQuery = connection.prepareStatement("select b.naziv, r.end from books b, users u, rentings r where r.renter = ? and r.book = b.id");
            addBookQuery = connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?, ?)");
            addRentQuery = connection.prepareStatement("INSERT INTO rentings VALUES (?, ?, ?, ?, ?)");
            booksQuery = connection.prepareStatement("SELECT * FROM books");
            restBooksQuery = connection.prepareStatement("select b.id, b.naziv, b.autor, b.zanr, b.broj_stranica, b.broj_knjiga - count(r.book) from books b, rentings r where r.book = b.id group by b.id;");
            removeBookQuery = connection.prepareStatement("DELETE FROM books WHERE id = ?");
            removeUserQuery = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            booksQuery = connection.prepareStatement("SELECT * FROM books");
            editBookQuery = connection.prepareStatement("UPDATE books SET naziv = ?, autor = ?, zanr = ?, broj_stranica = ?, broj_knjiga = ? WHERE id = ?");
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

            addUserQuery = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?)");

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

            addAdminQuery = connection.prepareStatement("INSERT INTO admins VALUES (?, ?, ?, ?, ?)");

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

    public Book getBookFromResultSet(ResultSet rs){

        try {
            return new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public HashMap<String, String> getUsersBooks(User user){

        HashMap<String, String> podignuteKnjige = new HashMap<>();

        try {
            getRentingsQuery.setString(1, user.getUsername());
            ResultSet rs = getRentingsQuery.executeQuery();

            while (rs.next()){
                //HashMap<String, String> help = new HashMap<>();
                podignuteKnjige.put(rs.getString(1), rs.getString(2));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return podignuteKnjige;
    }

    public void addBook(Book book){

        try {
            ResultSet rs = nextIdBookQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addBookQuery.setInt(1, id);
            addBookQuery.setString(2, book.getName());
            addBookQuery.setString(3, book.getAuthor());
            addBookQuery.setString(4, book.getGenre());
            addBookQuery.setInt(5, book.getBrojStranica());
            addBookQuery.setInt(6, book.getBrojKnjiga());

            addBookQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Book> books(){
        ArrayList<Book> knjige = new ArrayList<>();
        try {
            ResultSet rs = booksQuery.executeQuery();
            while (rs.next()){
                knjige.add(getBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return knjige;
    }

    public ArrayList<Book> getRestBooks(){

        ArrayList<Book> preostaleKnjige = new ArrayList<>();

        try {
            ResultSet rs = restBooksQuery.executeQuery();

            while(rs.next()){
                preostaleKnjige.add(getBookFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preostaleKnjige;
    }

    public void removeBook (Book book) {
        try {
            removeBookQuery.setInt(1, book.getId());
            removeBookQuery.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUser (User user) {
        try {
            removeUserQuery.setString(1, user.getUsername());
            removeUserQuery.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editBook (Book book) {
        try {
            editBookQuery.setString(1, book.getName());
            editBookQuery.setString(2, book.getAuthor());
            editBookQuery.setString(3, book.getGenre());
            editBookQuery.setInt(4, book.getBrojStranica());
            editBookQuery.setInt(5, book.getBrojKnjiga());
            editBookQuery.setInt(6, book.getId());

            editBookQuery.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addRent (Renting r) {

        try {

        ResultSet rs = nextIdRentQuery.executeQuery();
        int id = 1;
        if (rs.next()) {
            id = rs.getInt(1);
        }

        addRentQuery.setInt(1, id);
        addRentQuery.setString(2, r.getIznajmljivac().getUsername());
        addRentQuery.setInt(3, r.getKnjiga().getId());
        addRentQuery.setString(4, r.getPocetak().toString());
        addRentQuery.setString(5, r.getKraj().toString());

        addRentQuery.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



}
