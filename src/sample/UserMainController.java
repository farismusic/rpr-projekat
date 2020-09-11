package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserMainController {


    public MenuItem odjaviSe;

    public TableView<Renting> tableView;
    public TableColumn columnNaziv;
    public TableColumn<Renting, LocalDateTime> columnRok;

    public ListView<Notification> listViewObavijesti;

    private BibliotekaDAO baza;
    private User user;
    private ObservableList<Renting> iznajmljeneKnjige;
    private ObservableList<Notification> notifications;

    public UserMainController(User user) {
        baza = BibliotekaDAO.getInstance();
        this.user = user;
        iznajmljeneKnjige = FXCollections.observableArrayList(baza.usersRentings(user));
    }

    @FXML
    public void initialize(){

        listViewObavijesti.setItems(FXCollections.observableArrayList(checkNotifications()));

        tableView.setItems(iznajmljeneKnjige);
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        columnNaziv.setCellValueFactory(new PropertyValueFactory<>("knjiga"));
        columnRok.setCellValueFactory(new PropertyValueFactory<>("kraj"));

        columnRok.setCellFactory((TableColumn<Renting, LocalDateTime> column) -> {
            return new TableCell<Renting, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.format(formater));
                    }
                }
            };
        });


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

    public ArrayList<Notification> checkNotifications() {

        ArrayList<Notification> n = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for(Renting r : iznajmljeneKnjige) {

            if (r.getKraj().isBefore(now)) n.add(new Notification(r));

        }

        return n;

    }


}
