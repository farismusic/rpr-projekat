package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserBooksController {

    public TableView<Book> tableView;
    public TableColumn columnNaziv;
    public TableColumn columnAutor;
    public TableColumn columnZanr;
    public TableColumn columnBrojStranica;
    public TableColumn columnDostupnih;
    private BibliotekaDAO baza;
    private ObservableList<Book> knjige;


    public UserBooksController() {
        baza = BibliotekaDAO.getInstance();
        knjige = FXCollections.observableArrayList(baza.getRestBooks());
    }

    @FXML
    public void initialize(){
        tableView.setItems(knjige);

        columnNaziv.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAutor.setCellValueFactory(new PropertyValueFactory<>("author"));
        columnZanr.setCellValueFactory(new PropertyValueFactory<>("genre"));
        columnBrojStranica.setCellValueFactory(new PropertyValueFactory<>("numberOfPages"));
        columnDostupnih.setCellValueFactory(new PropertyValueFactory<>("numberOfBooks"));
    }
}
