package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class RentBookControler {

    private ObservableList<Book> knjige;
    private Renting renting;
    private User user;

    public ChoiceBox<Book> choiceBoxBooks;

    public RentBookControler(ArrayList<Book> knjige, User user) {
        this.knjige = FXCollections.observableArrayList(knjige);
        this.user = user;
    }

    @FXML
    public void initialize(){

        choiceBoxBooks.setItems(knjige);

    }

    public void actionDajKnjigu (ActionEvent actionEvent) {
        System.out.println("Blee");
    }




    public Renting getRenting() {
        return renting;
    }

    public RentBookControler setRenting(Renting renting) {
        this.renting = renting;
        return this;
    }
}
