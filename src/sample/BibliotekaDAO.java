package sample;

import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BibliotekaDAO {

    private static BibliotekaDAO instance;
    private Connection connection;
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");
    private PreparedStatement addUserQuery, addAdminQuery, findAdminQuery, findUserQuery, adminsQuery, usersQuery, nextIdBookQuery, getRentingsQuery, nextIdRentQuery,
            addBookQuery, booksQuery, removeBookQuery, removeUserQuery, editBookQuery, addRentQuery, useBookQuery, usersRentingsQuery, findBookQuery, removeRentQuery,
            removeAdminQuery;

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
            nextIdBookQuery = connection.prepareStatement("SELECT max(id) FROM books");
            nextIdRentQuery = connection.prepareStatement("SELECT max(id) FROM rentings");
            getRentingsQuery = connection.prepareStatement("select b.naziv, r.end from books b, users u, rentings r where r.renter = ? and r.book = b.id");
            addBookQuery = connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?, ?)");
            addRentQuery = connection.prepareStatement("INSERT INTO rentings VALUES (?, ?, ?, ?, ?)");
            booksQuery = connection.prepareStatement("SELECT * FROM books");
            removeBookQuery = connection.prepareStatement("DELETE FROM books WHERE id = ?");
            removeUserQuery = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            booksQuery = connection.prepareStatement("SELECT * FROM books");
            editBookQuery = connection.prepareStatement("UPDATE books SET naziv = ?, autor = ?, zanr = ?, broj_stranica = ?, broj_knjiga = ? WHERE id = ?");
            useBookQuery = connection.prepareStatement("SELECT count(book) FROM rentings WHERE book = ?");
            usersRentingsQuery = connection.prepareStatement("SELECT * FROM rentings WHERE renter = ?");
            findUserQuery = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            findBookQuery = connection.prepareStatement("SELECT * FROM books WHERE id = ?");
            removeRentQuery = connection.prepareStatement("DELETE FROM rentings WHERE id = ?");
            removeAdminQuery = connection.prepareStatement("DELETE FROM admins WHERE username = ?");
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
        boolean isThere = false;

        administrators.addAll(admins());


        for (Administrator a : administrators){
            if(a.getUsername().equals(user.getUsername()) || user.getUsername().equalsIgnoreCase("root")){
                isThere = true;
            }
        }

        try {

            if(isThere){
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

    public boolean addAdmin(Administrator administrator){


        ArrayList<User> users = new ArrayList<>();
        boolean isThere = false;

        users.addAll(users());


        for (User a : users){
            if(a.getUsername().equals(administrator.getUsername()) || administrator.getUsername().equalsIgnoreCase("root")){
                isThere = true;
            }
        }

        try {

            if(isThere){
                throw new SQLException();
            }

            addAdminQuery = connection.prepareStatement("INSERT INTO admins VALUES (?, ?, ?, ?, ?)");

            addAdminQuery.setString(1, administrator.getUsername());
            addAdminQuery.setString(2, administrator.getName());
            addAdminQuery.setString(3, administrator.getLastName());
            addAdminQuery.setString(4, administrator.getEmail());
            addAdminQuery.setString(5, administrator.getPassword());

            addAdminQuery.executeUpdate();

            return true;
        } catch (SQLException e) {
            alreadyExists(administrator.getUsername());
            return false;
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

    public User findUser (String username) {

        try {
            findUserQuery.setString(1, username);
            ResultSet rs = findUserQuery.executeQuery();
            return getUserFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new User();


    }

    public Book findBook (int id) {

        try {
            findBookQuery.setInt(1, id);
            ResultSet rs = findBookQuery.executeQuery();
            return getBookFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new Book();

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
        alert.setTitle(bundle.getString("error"));
        alert.setHeaderText(bundle.getString("username") + " " + username + " " + bundle.getString("exists"));
        alert.setContentText(bundle.getString("tryAgain"));
        alert.setResizable(true);
        alert.show();
    }



    public void addBook(Book book){

        try {
            ResultSet rs = nextIdBookQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }

            addBookQuery.setInt(1, id);
            addBookQuery.setString(2, book.getName());
            addBookQuery.setString(3, book.getAuthor());
            addBookQuery.setString(4, book.getGenre());
            addBookQuery.setInt(5, book.getNumberOfPages());
            addBookQuery.setInt(6, book.getNumberOfBooks());

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

    public List<Book> getRestBooks() {

        ArrayList<Book> restBooks = new ArrayList<>(books());

        return restBooks.stream().map(book -> book.setNumberOfBooks(book.getNumberOfBooks() - useBook(book.getId()))).collect(Collectors.toList());

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
            editBookQuery.setInt(4, book.getNumberOfPages());
            editBookQuery.setInt(5, book.getNumberOfBooks());
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
            id = rs.getInt(1) + 1;
        }

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


        addRentQuery.setInt(1, id);
        addRentQuery.setString(2, r.getRenter().getUsername());
        addRentQuery.setInt(3, r.getBook().getId());
        addRentQuery.setString(4, r.getDateBegin().format(formater));
        addRentQuery.setString(5, r.getDateEnd().format(formater));

        addRentQuery.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public int useBook (int id) {

        int i = 0;

        try {
            useBookQuery.setInt(1, id);
            ResultSet rs = useBookQuery.executeQuery();
            if(rs.next()) i = rs.getInt(1);
            return i;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return i;


    }

    public Renting getRentFromResultSet (ResultSet rs) throws SQLException {
        return new Renting(rs.getInt(1), findUser(rs.getString(2)), getLDTFromString(rs.getString(4)), getLDTFromString(rs.getString(5)), findBook(rs.getInt(3)));
    }

    public ArrayList<Renting> usersRentings (User user) {

        ArrayList<Renting> rentings = new ArrayList<>();

        try {
            usersRentingsQuery.setString(1, user.getUsername());
            ResultSet rs = usersRentingsQuery.executeQuery();

            while(rs.next()){
                rentings.add(getRentFromResultSet(rs));
            }

            return rentings;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rentings;

    }

    public LocalDateTime getLDTFromString (String string) {

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        LocalDateTime ldt = LocalDateTime.parse(string, formater);

        return ldt;


    }

    public void removeRent (Renting renting) {

        try {
            removeRentQuery.setInt(1, renting.getId());
            removeRentQuery.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void removeAdmin (Administrator administrator) {

        try {
            removeAdminQuery.setString(1, administrator.getUsername());
            removeAdminQuery.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}
