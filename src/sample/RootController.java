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

public class RootController {

    public TableView<Administrator> tableViewAdmini;
    public TableColumn columnIme;
    public TableColumn columnPrezime;
    public TableColumn columnUsername;
    private BibliotekaDAO baza;
    private ObservableList<Administrator> admini;

    public RootController() {
        baza = BibliotekaDAO.getInstance();
        admini = FXCollections.observableArrayList(baza.admins());
    }


    @FXML
    public void initialize(){

        tableViewAdmini.setItems(admini);

        columnIme.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPrezime.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

    }


    public void actionDodajAdmina(ActionEvent actionEvent) {

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            RegisterController registerController = new RegisterController(true);
            loader.setController(registerController);
            root = loader.load();
            stage.setTitle("Dodaj admina");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Administrator a = registerController.getAdmin();
                if (a != null) {
                    admini.clear();
                    admini.addAll(baza.admins());
                    tableViewAdmini.refresh();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void actionIzbrisiAdmina(ActionEvent actionEvent) {

        Administrator administrator = tableViewAdmini.getSelectionModel().getSelectedItem();
        if (administrator == null) return;

        baza.removeAdmin(administrator);

        admini.clear();
        admini.addAll(baza.admins());
        tableViewAdmini.refresh();

    }

    public void actionOdjaviteSe (ActionEvent actionEvent) {
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
        Stage stage = (Stage) tableViewAdmini.getScene().getWindow();
        stage.close();
    }

}
