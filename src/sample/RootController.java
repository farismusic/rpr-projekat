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

    public TableView<Administrator> tableViewAdmins;
    public TableColumn columnName;
    public TableColumn columnLastName;
    public TableColumn columnUsername;
    private BibliotekaDAO db;
    private ObservableList<Administrator> admins;

    public RootController() {
        db = BibliotekaDAO.getInstance();
        admins = FXCollections.observableArrayList(db.admins());
    }


    @FXML
    public void initialize(){

        tableViewAdmins.setItems(admins);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

    }


    public void actionAddAdmin(ActionEvent actionEvent) {

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
                    admins.clear();
                    admins.addAll(db.admins());
                    tableViewAdmins.refresh();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void actionRemoveAdmin(ActionEvent actionEvent) {

        Administrator administrator = tableViewAdmins.getSelectionModel().getSelectedItem();
        if (administrator == null) return;

        db.removeAdmin(administrator);

        admins.clear();
        admins.addAll(db.admins());
        tableViewAdmins.refresh();

    }

    public void actionLogOut(ActionEvent actionEvent) {
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
        Stage stage = (Stage) tableViewAdmins.getScene().getWindow();
        stage.close();
    }

}
