package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserMainController {

    public TableView<Renting> tableView;
    public TableColumn columnName;
    public TableColumn<Renting, LocalDateTime> columnDateEnd;

    public ListView<Notification> listViewNotifications;

    private BibliotekaDAO db;
    private User user;
    private ObservableList<Renting> rentedBooks;

    public UserMainController(User user) {
        db = BibliotekaDAO.getInstance();
        this.user = user;
        rentedBooks = FXCollections.observableArrayList(db.usersRentings(user));
    }

    @FXML
    public void initialize(){

        listViewNotifications.setItems(FXCollections.observableArrayList(checkNotifications()));

        tableView.setItems(rentedBooks);
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        columnName.setCellValueFactory(new PropertyValueFactory<>("book"));
        columnDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));

        columnDateEnd.setCellFactory((TableColumn<Renting, LocalDateTime> column) -> {
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

    public void logOutUser(ActionEvent actionEvent) {
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
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }

    public void showBooks(){
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/bookUser.fxml"), bundle);
            Stage primaryStage = new Stage();
            primaryStage.setTitle(bundle.getString("books"));
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

        for(Renting r : rentedBooks) {

            if (r.getDateEnd().isBefore(now)) n.add(new Notification(r));

        }

        return n;

    }


}
