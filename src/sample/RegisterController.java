package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {


    public Button btnRegister;
    public PasswordField fieldPasswordRepeat;
    public PasswordField fieldPassword;
    public TextField fieldEmail;
    public TextField fieldUsername;
    public TextField fieldPrezime;
    public TextField fieldIme;
    private BibliotekaDAO baza;

    public RegisterController() {
        baza = BibliotekaDAO.getInstance();
    }


    public void registruj(ActionEvent actionEvent) {
        baza.addUser(new User(fieldUsername.getText(), fieldIme.getText(), fieldPrezime.getText(), fieldEmail.getText(), fieldPassword.getText()));
    }
}
