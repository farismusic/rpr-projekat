package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UsersRentingsController {

    private ObservableList<Renting> rentings;
    public TableView<Renting> tableViewRentings;
    public TableColumn columnBook;
    public TableColumn<Renting, LocalDateTime> columnDateBegin;
    public TableColumn<Renting, LocalDateTime> columnDateEnd;
    private Renting renting;

    public Renting getRenting() {
        return renting;
    }

    public UsersRentingsController setRenting(Renting renting) {
        this.renting = renting;
        return this;
    }

    public UsersRentingsController(ArrayList<Renting> rentings) {
        this.rentings = FXCollections.observableArrayList(rentings);
    }

    @FXML
    public void initialize(){



        tableViewRentings.setItems(rentings);
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


        columnBook.setCellValueFactory(new PropertyValueFactory<>("book"));
        columnDateBegin.setCellValueFactory(new PropertyValueFactory<>("dateBegin"));
        columnDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));

        columnDateBegin.setCellFactory((TableColumn<Renting, LocalDateTime> column) -> {
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

    public void actionGiveBackBook(ActionEvent actionEvent) {

        renting = tableViewRentings.getSelectionModel().getSelectedItem();
        if(renting == null) return;

        closeWindow();

    }

    private void closeWindow(){
        Stage stage = (Stage) tableViewRentings.getScene().getWindow();
        stage.close();
    }
}
