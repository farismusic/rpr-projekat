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

}
