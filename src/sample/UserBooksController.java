package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserBooksController {

    public TableView<Book> tableView;
    public TableColumn columnName;
    public TableColumn columnAuthor;
    public TableColumn columnGenre;
    public TableColumn columnNumberOfPages;
    public TableColumn columnAvailable;
    private BibliotekaDAO db;
    private ObservableList<Book> books;


    public UserBooksController() {
        db = BibliotekaDAO.getInstance();
        books = FXCollections.observableArrayList(db.getRestBooks());
    }

    @FXML
    public void initialize(){
        tableView.setItems(books);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        columnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        columnNumberOfPages.setCellValueFactory(new PropertyValueFactory<>("numberOfPages"));
        columnAvailable.setCellValueFactory(new PropertyValueFactory<>("numberOfBooks"));
    }
}
