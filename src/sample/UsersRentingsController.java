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
    public TableView<Renting> tableViewDizanja;
    public TableColumn columnKnjiga;
    public TableColumn<Renting, LocalDateTime> columnPocetak;
    public TableColumn<Renting, LocalDateTime> columnKraj;
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



        tableViewDizanja.setItems(rentings);
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


        columnKnjiga.setCellValueFactory(new PropertyValueFactory<>("knjiga"));
        columnPocetak.setCellValueFactory(new PropertyValueFactory<>("pocetak"));
        columnKraj.setCellValueFactory(new PropertyValueFactory<>("kraj"));

        columnPocetak.setCellFactory((TableColumn<Renting, LocalDateTime> column) -> {
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

        columnKraj.setCellFactory((TableColumn<Renting, LocalDateTime> column) -> {
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

    public void actionVratiKnjigu (ActionEvent actionEvent) {

        renting = tableViewDizanja.getSelectionModel().getSelectedItem();
        if(renting == null) return;

        closeWindow();

    }

    private void closeWindow(){
        Stage stage = (Stage) tableViewDizanja.getScene().getWindow();
        stage.close();
    }
}
