package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdministratorMainControler {

    public TableView<Book> tableViewKnjige;
    public TableColumn columnNaziv;
    public TableColumn columnBrojStranica;
    public TableColumn columnDostupnih;

    public TableView<User> tableViewKorisnici;
    public TableColumn columnKorisnickoIme;

    private BibliotekaDAO baza;
    private ObservableList<Book> knjige;
    private ObservableList<User> korisnici;
    private Administrator admin;

    public AdministratorMainControler(Administrator admin) {
        this.admin = admin;
        baza = BibliotekaDAO.getInstance();
        knjige = FXCollections.observableArrayList(baza.getRestBooks());
        korisnici = FXCollections.observableArrayList(baza.users());
    }

    @FXML
    public void initialize() {

        tableViewKnjige.setItems(knjige);

        columnNaziv.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnBrojStranica.setCellValueFactory(new PropertyValueFactory<>("brojStranica"));
        columnDostupnih.setCellValueFactory(new PropertyValueFactory<>("brojKnjiga"));

        tableViewKorisnici.setItems(korisnici);
        columnKorisnickoIme.setCellValueFactory(new PropertyValueFactory<>("username"));

    }


    public void odjaviAdmina (ActionEvent actionEvent) {
        closeWindow();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 250, 225));
            primaryStage.setResizable(false);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) tableViewKorisnici.getScene().getWindow();
        stage.close();
    }

    public void actionDodajKnjigu(ActionEvent actionEvent) {


        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/book.fxml"));
            BookController bookController = new BookController();
            loader.setController(bookController);
            root = loader.load();
            stage.setTitle("Dodaj knjigu");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Book b = bookController.getBook();
                if (b != null) {
                    baza.addBook(b);
                    knjige.clear();
                    knjige.addAll(baza.books());
                    tableViewKnjige.refresh();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionIzbrisiKnjigu (ActionEvent actionevent) {

        Book book = tableViewKnjige.getSelectionModel().getSelectedItem();
        if (book != null) {
            baza.removeBook(book);

            knjige.clear();
            knjige.addAll(baza.books());

            tableViewKnjige.refresh();
        }

    }

    public void actionIzmijeniKnjigu (ActionEvent actionEvent) {

        Book b = tableViewKnjige.getSelectionModel().getSelectedItem();

        if (b == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/book.fxml"));
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

                    baza.editBook(book);
                    knjige.clear();
                    knjige.addAll(baza.books());
                    tableViewKnjige.refresh();
                    
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionIzbrisiKorisnika (ActionEvent actionEvent) {

        User user = tableViewKorisnici.getSelectionModel().getSelectedItem();
        if(user != null) {
            baza.removeUser(user);

            korisnici.clear();
            korisnici.addAll(baza.users());

            tableViewKorisnici.refresh();
        }

    }

    public void actionUzmiKnjigu(ActionEvent actionEvent) {

        User u = tableViewKorisnici.getSelectionModel().getSelectedItem();

        if (u == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/rentbook.fxml"));
            RentBookControler rentBookController = new RentBookControler(baza.books(), u);
            loader.setController(rentBookController);
            root = loader.load();
            stage.setTitle("Iznajmljivanje knjige za korisnika " + u.getUsername());
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Renting renting = rentBookController.getRenting();
                if (renting != null) {
                    baza.addRent(renting);
                    knjige.clear();
                    knjige.addAll(baza.getRestBooks());
                    tableViewKnjige.refresh();

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionVratiKnjigu (ActionEvent actionEvent) {

        User user = tableViewKorisnici.getSelectionModel().getSelectedItem();
        if(user == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/usersRentings.fxml"));
            UsersRentingsController usersRentingsController = new UsersRentingsController(baza.usersRentings(user));
            loader.setController(usersRentingsController);
            root = loader.load();
            stage.setTitle("Iznajmljivanja korisnika " + user.getUsername());
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Renting r = usersRentingsController.getRenting();
                if (r != null) {
                    baza.removeRent(r);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
