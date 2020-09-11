package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class RentBookControler {

    private ObservableList<Book> books;
    private Renting renting;
    private User user;
    private BibliotekaDAO db;

    public ChoiceBox<Book> choiceBoxBooks;

    public RentBookControler(ArrayList<Book> books, User user) {

        db = BibliotekaDAO.getInstance();

        Set<Book> set = new TreeSet<>();
        set.addAll(books);

        this.books = FXCollections.observableArrayList(set);
        this.user = user;
    }

    @FXML
    public void initialize(){

        choiceBoxBooks.setItems(books);

    }

    public void actionRentBook(ActionEvent actionEvent) {

        Book book = choiceBoxBooks.getValue();
        if (book == null) return;

        if (areThereAnyAvailable(book)) {

            renting = new Renting();
            renting.setRenter(user);
            renting.setBook(book);
            renting.setDateBegin(LocalDateTime.now());
            renting.setDateEnd(renting.getDateBegin().plusDays(7));

            closeWindow();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Nedostupno: " + "\" " + book.getName() + " \"");
            alert.setContentText("Trenutno nema dostupnih knjiga" + "\" " + book.getName() + " \"");
            alert.setResizable(true);
            alert.show();

        }

    }


    public Renting getRenting() {
        return renting;
    }

    public RentBookControler setRenting(Renting renting) {
        this.renting = renting;
        return this;
    }

    public boolean areThereAnyAvailable(Book book) {

        if((book.getNumberOfBooks() - db.useBook(book.id)) == 0) return false;
        else return true;

    }

    public void closeWindow(){
        Stage stage = (Stage) choiceBoxBooks.getScene().getWindow();
        stage.close();
    }
}
