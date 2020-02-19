package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {


    public Button btnRegister;
    public PasswordField fieldPasswordRepeat;
    public PasswordField fieldPassword;
    public TextField fieldEmail;
    public TextField fieldUsername;
    public TextField fieldLastName;
    public TextField fieldName;
    private BibliotekaDAO baza;
    private User user;

    public RegisterController() {
        baza = BibliotekaDAO.getInstance();
        user = new User();
    }

    public void registruj(ActionEvent actionEvent) {

        boolean sveOk = true;

        if (fieldUsername.getText().trim().isEmpty()) {
            fieldUsername.getStyleClass().removeAll("poljeIspravno");
            fieldUsername.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldUsername.getStyleClass().removeAll("poljeNijeIspravno");
            fieldUsername.getStyleClass().add("poljeIspravno");
        }

        if (fieldName.getText().trim().isEmpty()) {
            fieldName.getStyleClass().removeAll("poljeIspravno");
            fieldName.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldName.getStyleClass().removeAll("poljeNijeIspravno");
            fieldName.getStyleClass().add("poljeIspravno");
        }

        if (fieldLastName.getText().trim().isEmpty()) {
            fieldLastName.getStyleClass().removeAll("poljeIspravno");
            fieldLastName.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldLastName.getStyleClass().removeAll("poljeNijeIspravno");
            fieldLastName.getStyleClass().add("poljeIspravno");
        }

        if (fieldEmail.getText().trim().isEmpty()) {
            fieldEmail.getStyleClass().removeAll("poljeIspravno");
            fieldEmail.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldEmail.getStyleClass().removeAll("poljeNijeIspravno");
            fieldEmail.getStyleClass().add("poljeIspravno");
        }

        if (fieldPassword.getText().trim().isEmpty()) {
            fieldPassword.getStyleClass().removeAll("poljeIspravno");
            fieldPassword.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
            fieldPassword.getStyleClass().add("poljeIspravno");
        }

        if (fieldPasswordRepeat.getText().trim().isEmpty()) {
            fieldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
            fieldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
            fieldPasswordRepeat.getStyleClass().add("poljeIspravno");
        }

        if(!fieldPassword.getText().equals(fieldPasswordRepeat.getText())){
            fieldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
            fieldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }

        if(!sveOk) return;

        user.setUsername(fieldUsername.getText());
        user.setName(fieldName.getText());
        user.setLastName(fieldLastName.getText());
        user.setEmail( fieldEmail.getText());
        user.setPassword(fieldPassword.getText());


        baza.addUser(user);

        closeWindow();

    }

    public User getUser(){
        return user;
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }
}
