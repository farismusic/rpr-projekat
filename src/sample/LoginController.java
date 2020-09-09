package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {

    public Hyperlink hyperlinkRegister;
    public TextField fieldUsername;
    public PasswordField fieldPassword;
    public Button btnLogin;
    private BibliotekaDAO baza;
    private Person person;

    public LoginController() {
        baza = BibliotekaDAO.getInstance();
        person = new Person();
    }

    public void otvoriRegistraciju(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Register");
            primaryStage.setScene(new Scene(root, 500, 300));
            primaryStage.setResizable(false);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void login(ActionEvent actionEvent) {
        boolean sveOk = true;

        if (fieldUsername.getText().trim().isEmpty()) {
            fieldUsername.getStyleClass().removeAll("poljeIspravno");
            fieldUsername.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldUsername.getStyleClass().removeAll("poljeNijeIspravno");
            fieldUsername.getStyleClass().add("poljeIspravno");
        }

        if (fieldPassword.getText().trim().isEmpty()) {
            fieldPassword.getStyleClass().removeAll("poljeIspravno");
            fieldPassword.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
            fieldPassword.getStyleClass().add("poljeIspravno");
        }

        person.setUsername(fieldUsername.getText());
        person.setPassword(fieldPassword.getText());
        Person p = baza.find(person);

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Korisnik ne postoji");
            alert.setContentText("Pokušajte ponovo");
            alert.setResizable(true);
            alert.show();
        } else if (p instanceof Administrator) {

            closeWindow();

            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminMain.fxml"));
                AdministratorMainControler adminMainController = new AdministratorMainControler((Administrator) p);
                loader.setController(adminMainController);
                root = loader.load();
                stage.setTitle(p.getUsername());
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(false);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (p instanceof User) {
            closeWindow();

            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userMain.fxml"));
                UserMainController userMainController = new UserMainController((User) p);
                loader.setController(userMainController);
                root = loader.load();
                stage.setTitle(p.getUsername());
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(false);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldUsername.getScene().getWindow();
        stage.close();
    }




}
