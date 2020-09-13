package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdministratorMainControler {

    public TableView<Book> tableViewBooks;
    public TableColumn columnName;
    public TableColumn columnNumberOfPages;
    public TableColumn columnAvailable;
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");

    public TableView<User> tableViewUsers;
    public TableColumn columnUsername;

    private BibliotekaDAO db;
    private ObservableList<Book> books;
    private ObservableList<User> users;
    private Administrator admin;

    public AdministratorMainControler(Administrator admin) {
        this.admin = admin;
        db = BibliotekaDAO.getInstance();
        books = FXCollections.observableArrayList(db.getRestBooks());
        users = FXCollections.observableArrayList(db.users());
    }

    @FXML
    public void initialize() {

        tableViewBooks.setItems(books);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnNumberOfPages.setCellValueFactory(new PropertyValueFactory<>("numberOfPages"));
        columnAvailable.setCellValueFactory(new PropertyValueFactory<>("numberOfBooks"));

        tableViewUsers.setItems(users);
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

    }


    public void logOutAdmin(ActionEvent actionEvent) {
        closeWindow();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
            Stage primaryStage = new Stage();
            primaryStage.setTitle(bundle.getString("login"));
            primaryStage.setScene(new Scene(root, 250, 225));
            primaryStage.setResizable(false);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) tableViewUsers.getScene().getWindow();
        stage.close();
    }

    public void actionAddBook(ActionEvent actionEvent) {


        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/book.fxml"), bundle);
            BookController bookController = new BookController();
            loader.setController(bookController);
            root = loader.load();
            stage.setTitle(bundle.getString("addBook"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Book b = bookController.getBook();
                if (b != null) {
                    db.addBook(b);
                    books.clear();
                    books.addAll(db.books());
                    tableViewBooks.refresh();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionRemoveBook(ActionEvent actionevent) {

        Book book = tableViewBooks.getSelectionModel().getSelectedItem();
        if (book == null) return;

        if (db.useBook(book.getId()) != 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Knjigu " + "\" " + book.getName() + " \"" + " se ne može izbrisati");
            alert.setContentText("Knjiga " + "\" " + book.getName() + " \"" + " nije vraćena od svih korisnika");
            alert.setResizable(true);
            alert.show();
            return;
        }

        if (book != null) {
            db.removeBook(book);

            books.clear();
            books.addAll(db.books());

            tableViewBooks.refresh();
        }

    }

    public void actionEditBook(ActionEvent actionEvent) {

        Book b = tableViewBooks.getSelectionModel().getSelectedItem();

        if (b == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/book.fxml"), bundle);
            BookController bookController = new BookController(b);
            loader.setController(bookController);
            root = loader.load();
            stage.setTitle(b.getName());
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Book book = bookController.getBook();
                if (book != null) {

                    db.editBook(book);
                    books.clear();
                    books.addAll(db.books());
                    tableViewBooks.refresh();
                    
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionRemoveUser(ActionEvent actionEvent) {

        User user = tableViewUsers.getSelectionModel().getSelectedItem();
        if(user == null) return;

        if (db.usersRentings(user).size() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Korisnika " + user.getUsername() + " se ne može izbrisati");
            alert.setContentText("Korisnik " + user.getUsername() + " nije izmirio svoje obaveze prema biblioteci");
            alert.setResizable(true);
            alert.show();
            return;
        }

        if(user != null) {
            db.removeUser(user);

            users.clear();
            users.addAll(db.users());

            tableViewUsers.refresh();
        }

    }

    public void actionGetBook(ActionEvent actionEvent) {

        User u = tableViewUsers.getSelectionModel().getSelectedItem();

        if (u == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/rentBook.fxml"), bundle);
            RentBookControler rentBookController = new RentBookControler(db.books(), u);
            loader.setController(rentBookController);
            root = loader.load();
            stage.setTitle(bundle.getString("rentForUser") + u.getUsername());
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Renting renting = rentBookController.getRenting();
                if (renting != null) {
                    db.addRent(renting);
                    books.clear();
                    books.addAll(db.getRestBooks());
                    tableViewBooks.refresh();

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionGiveBackBook(ActionEvent actionEvent) {

        User user = tableViewUsers.getSelectionModel().getSelectedItem();
        if(user == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/usersRentings.fxml"), bundle);
            UsersRentingsController usersRentingsController = new UsersRentingsController(db.usersRentings(user));
            loader.setController(usersRentingsController);
            root = loader.load();
            stage.setTitle(bundle.getString("rentsUser") + user.getUsername());
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Renting r = usersRentingsController.getRenting();
                if (r != null) {
                    db.removeRent(r);
                    books.clear();
                    books.addAll(db.getRestBooks());
                    tableViewBooks.refresh();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
