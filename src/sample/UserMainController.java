package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Map;

public class UserMainController {


    public MenuItem odjaviSe;

    public TableView<Map.Entry<String,String>> tableView;
    public TableColumn<Map.Entry<String, String>, String> columnNaziv;
    public TableColumn<Map.Entry<String, String>, String> columnRok;

    private BibliotekaDAO baza;
    private User user;
    private ObservableList<Map.Entry<String, String>> iznajmljeneKnjige;

    public UserMainController(User user) {
        baza = BibliotekaDAO.getInstance();
        this.user = user;
        iznajmljeneKnjige = FXCollections.observableArrayList(baza.getUsersBooks(user).entrySet());
    }

    @FXML
    public void initialize(){

        tableView.setItems(iznajmljeneKnjige);

        columnNaziv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                // this callback returns property for just one cell, you can't use a loop here
                // for first column we use key
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });

        columnRok.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                // for second column we use value
                return new SimpleStringProperty(p.getValue().getValue());
            }
        });

        tableView.getColumns().setAll(columnNaziv, columnRok);

    }

    public void odjaviKorisnika(ActionEvent actionEvent) {
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
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }

    public void showBooks(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/bookUser.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Knjige");
            primaryStage.setScene(new Scene(root, 700, 400));
            primaryStage.setResizable(false);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
