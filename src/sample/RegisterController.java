package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
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
    private BibliotekaDAO db;
    private User user;
    private Administrator admin;
    private boolean type = false;

    public RegisterController(boolean isAdmin) {
        db = BibliotekaDAO.getInstance();
        user = new User();
        admin = new Administrator();
        type = isAdmin;
    }

    public void register(ActionEvent actionEvent) {

        boolean allOk = true;

        if (fieldUsername.getText().trim().isEmpty() || fieldUsername.getText().length() <= 5) {
            fieldUsername.getStyleClass().removeAll("poljeIspravno");
            fieldUsername.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldUsername.getStyleClass().removeAll("poljeNijeIspravno");
            fieldUsername.getStyleClass().add("poljeIspravno");
        }

        if (fieldName.getText().trim().isEmpty()) {
            fieldName.getStyleClass().removeAll("poljeIspravno");
            fieldName.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldName.getStyleClass().removeAll("poljeNijeIspravno");
            fieldName.getStyleClass().add("poljeIspravno");
        }

        if (fieldLastName.getText().trim().isEmpty()) {
            fieldLastName.getStyleClass().removeAll("poljeIspravno");
            fieldLastName.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldLastName.getStyleClass().removeAll("poljeNijeIspravno");
            fieldLastName.getStyleClass().add("poljeIspravno");
        }

        if (fieldEmail.getText().trim().isEmpty() || !isValidEmail(fieldEmail.getText())) {
            fieldEmail.getStyleClass().removeAll("poljeIspravno");
            fieldEmail.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldEmail.getStyleClass().removeAll("poljeNijeIspravno");
            fieldEmail.getStyleClass().add("poljeIspravno");
        }

        if (fieldPassword.getText().trim().isEmpty() || fieldPassword.getText().length() < 8 || fieldPassword.getText().length() > 16) {
            fieldPassword.getStyleClass().removeAll("poljeIspravno");
            fieldPassword.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
            fieldPassword.getStyleClass().add("poljeIspravno");
        }

        if (fieldPasswordRepeat.getText().trim().isEmpty() || fieldPassword.getText().length() < 8 || fieldPassword.getText().length() > 16) {
            fieldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
            fieldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        } else {
            fieldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
            fieldPasswordRepeat.getStyleClass().add("poljeIspravno");
        }

        if(!fieldPassword.getText().equals(fieldPasswordRepeat.getText())){
            fieldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
            fieldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            allOk = false;
        }

        if(!allOk) return;

        if (!type) {
            user.setUsername(fieldUsername.getText());
            user.setName(fieldName.getText());
            user.setLastName(fieldLastName.getText());
            user.setEmail(fieldEmail.getText());
            user.setPassword(fieldPassword.getText());
        } else {
            admin.setUsername(fieldUsername.getText());
            admin.setName(fieldName.getText());
            admin.setLastName(fieldLastName.getText());
            admin.setEmail(fieldEmail.getText());
            admin.setPassword(fieldPassword.getText());
        }


        if(!type && db.addUser(user)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješna registracija");
            alert.setHeaderText("Uspješno ste kreirali korisnički profil");
            alert.setContentText("Prijavite se na vaš profil");
            alert.setResizable(true);
            alert.show();
            closeWindow();
        } else if (type){
            db.addAdmin(admin);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješna registracija");
            alert.setHeaderText("Uspješno ste kreirali admin profil");
            alert.setContentText("Admin se može ulogovati na svoj profil");
            alert.setResizable(true);
            alert.show();
            closeWindow();

        }

    }

    public User getUser(){
        return user;
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }

    protected boolean isValidEmail(String s) {
        if (!s.contains("@")) {
            return false;
        } else {
            String[] email = s.split("@");
            if (email.length < 2) return false;
            boolean isThereLetter = false;
            for (char c : email[0].toCharArray()) {
                if (Character.isLetter(c)) {
                    isThereLetter = true;
                    break;
                }
            }
            if (!isThereLetter) return false;
            isThereLetter = false;
            for (char c : email[1].toCharArray()) {
                if (Character.isLetter(c)) {
                    isThereLetter = true;
                    break;
                }
            }
            if (!isThereLetter) return false;
        }
        return true;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public RegisterController setAdmin(Administrator admin) {
        this.admin = admin;
        return this;
    }
}
