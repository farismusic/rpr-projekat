package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class BookController {

    private Book book;

    public TextField fieldName, fieldAuthor, fieldGenre, fieldNumberOfPages, fieldNumberOfBooks;

    public BookController(Book book) {
        this.book = book;
    }

    public BookController() {
    }

    @FXML
    public void initialize(){

        if(book != null) {

            fieldName.setText(book.getName());
            fieldGenre.setText(book.getGenre());
            fieldAuthor.setText(book.getAuthor());
            fieldNumberOfPages.setText(Integer.toString(book.getNumberOfPages()));
            fieldNumberOfBooks.setText(Integer.toString(book.getNumberOfBooks()));

        }

    }

    public Book getBook() {
        return book;
    }

    public BookController setBook(Book book) {
        this.book = book;
        return this;
    }

    public void actionAdd(ActionEvent actionEvent){

        boolean allOk = true;

        if (fieldName.getText().trim().isEmpty()) {
            fieldName.getStyleClass().removeAll("poljeIspravno");
            fieldName.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldName.getStyleClass().removeAll("poljeNijeIspravno");
            fieldName.getStyleClass().add("poljeIspravno");
        }

        if (fieldAuthor.getText().trim().isEmpty()) {
            fieldAuthor.getStyleClass().removeAll("poljeIspravno");
            fieldAuthor.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldAuthor.getStyleClass().removeAll("poljeNijeIspravno");
            fieldAuthor.getStyleClass().add("poljeIspravno");
        }

        if (fieldGenre.getText().trim().isEmpty()) {
            fieldGenre.getStyleClass().removeAll("poljeIspravno");
            fieldGenre.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldGenre.getStyleClass().removeAll("poljeNijeIspravno");
            fieldGenre.getStyleClass().add("poljeIspravno");
        }

        int numPag = 1;
        try {
            numPag = Integer.parseInt(fieldNumberOfPages.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (fieldNumberOfPages.getText().trim().isEmpty() || numPag < 1) {
            fieldNumberOfPages.getStyleClass().removeAll("poljeIspravno");
            fieldNumberOfPages.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldNumberOfPages.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNumberOfPages.getStyleClass().add("poljeIspravno");
        }

        int numBook = 1;
        try {
            numBook = Integer.parseInt(fieldNumberOfBooks.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (fieldNumberOfBooks.getText().trim().isEmpty() || numBook < 1) {
            fieldNumberOfBooks.getStyleClass().removeAll("poljeIspravno");
            fieldNumberOfBooks.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldNumberOfBooks.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNumberOfBooks.getStyleClass().add("poljeIspravno");
        }

        if(!allOk) return;

        if (book == null) book = new Book();

        book.setName(fieldName.getText().trim());
        book.setAuthor(fieldAuthor.getText().trim());
        book.setGenre(fieldGenre.getText().trim());
        book.setNumberOfPages(Integer.parseInt(fieldNumberOfPages.getText().trim()));
        book.setNumberOfBooks(Integer.parseInt(fieldNumberOfBooks.getText().trim()));

        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();

    }


}
