package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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

        boolean sveOk = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }

        if (fieldAutor.getText().trim().isEmpty()) {
            fieldAutor.getStyleClass().removeAll("poljeIspravno");
            fieldAutor.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldAutor.getStyleClass().removeAll("poljeNijeIspravno");
            fieldAutor.getStyleClass().add("poljeIspravno");
        }

        if (fieldZanr.getText().trim().isEmpty()) {
            fieldZanr.getStyleClass().removeAll("poljeIspravno");
            fieldZanr.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldZanr.getStyleClass().removeAll("poljeNijeIspravno");
            fieldZanr.getStyleClass().add("poljeIspravno");
        }

        int brojStranica = 1;
        try {
            brojStranica = Integer.parseInt(fieldBrojStranica.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (fieldBrojStranica.getText().trim().isEmpty() || brojStranica < 1) {
            fieldBrojStranica.getStyleClass().removeAll("poljeIspravno");
            fieldBrojStranica.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldBrojStranica.getStyleClass().removeAll("poljeNijeIspravno");
            fieldBrojStranica.getStyleClass().add("poljeIspravno");
        }

        int brojKnjiga = 1;
        try {
            brojKnjiga = Integer.parseInt(fieldBrojKnjiga.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (fieldBrojKnjiga.getText().trim().isEmpty() || brojKnjiga < 1) {
            fieldBrojKnjiga.getStyleClass().removeAll("poljeIspravno");
            fieldBrojKnjiga.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldBrojKnjiga.getStyleClass().removeAll("poljeNijeIspravno");
            fieldBrojKnjiga.getStyleClass().add("poljeIspravno");
        }

        if(!sveOk) return;

        if (book == null) book = new Book();

        book.setName(fieldNaziv.getText().trim());
        book.setAuthor(fieldAutor.getText().trim());
        book.setGenre(fieldZanr.getText().trim());
        book.setBrojStranica(Integer.parseInt(fieldBrojStranica.getText().trim()));
        book.setBrojKnjiga(Integer.parseInt(fieldBrojKnjiga.getText().trim()));

        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();

    }


}
