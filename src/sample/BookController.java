package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class BookController {

    private Book book;

    public TextField fieldNaziv, fieldAutor, fieldZanr, fieldBrojStranica, fieldBrojKnjiga;

    public BookController(Book book) {
        this.book = book;
    }

    public BookController() {
    }

    @FXML
    public void initialize(){

        if(book != null) {

            fieldNaziv.setText(book.getName());
            fieldZanr.setText(book.getGenre());
            fieldAutor.setText(book.getAuthor());
            fieldBrojStranica.setText(Integer.toString(book.getBrojStranica()));
            fieldBrojKnjiga.setText(Integer.toString(book.getBrojKnjiga()));

        }

    }

    public Book getBook() {
        return book;
    }

    public BookController setBook(Book book) {
        this.book = book;
        return this;
    }

    public void actionDodaj (ActionEvent actionEvent){

    }
}
