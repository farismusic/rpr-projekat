package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {

    public Hyperlink hyperlinkRegister;
    public TextField fieldUsername;
    public PasswordField fieldPassword;
    public Button btnLogin;
    public Label labelUsername;
    private BibliotekaDAO db;
    private Person person;
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");


    public LoginController() {
        db = BibliotekaDAO.getInstance();
        person = new Person();
    }



    public void otvoriRegistraciju(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"), bundle);
            RegisterController registerController = new RegisterController(false);
            loader.setController(registerController);
            root = loader.load();
            stage.setTitle(bundle.getString("addUser"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            /*stage.setOnHiding(event -> {
                Administrator a = registerController.getAdmin();
                if (a != null) {
                    admini.clear();
                    admini.addAll(baza.admins());
                    tableViewAdmini.refresh();
                }
            });*/

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void login(ActionEvent actionEvent) {


        AtomicBoolean sveOk = new AtomicBoolean(true);

        Thread thread1 = new Thread(() -> {
            if (fieldUsername.getText().trim().isEmpty()) {
                fieldUsername.getStyleClass().removeAll("poljeIspravno");
                fieldUsername.getStyleClass().add("poljeNijeIspravno");
                sveOk.set(false);
            } else {
                fieldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fieldUsername.getStyleClass().add("poljeIspravno");
            }
        });


        Thread thread2 = new Thread(() -> {
            if (fieldPassword.getText().trim().isEmpty()) {
                fieldPassword.getStyleClass().removeAll("poljeIspravno");
                fieldPassword.getStyleClass().add("poljeNijeIspravno");
                sveOk.set(false);
            } else {
                fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fieldPassword.getStyleClass().add("poljeIspravno");
            }
        });

        thread1.run();
        thread2.run();



        person.setUsername(fieldUsername.getText());
        person.setPassword(fieldPassword.getText());
        Person p = db.find(person);

        if(fieldUsername.getText().trim().equals("root") && fieldPassword.getText().trim().equals("%&/123RooT123()=")){

            closeWindow();

            Stage stage = new Stage();
            Parent root = null;
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"), bundle);
                RootController rootController = new RootController();
                loader.setController(rootController);
                root = loader.load();
                stage.setTitle("Root");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(false);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (p == null) {
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminMain.fxml"), bundle);
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userMain.fxml"), bundle);
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

    public void actionChangeLanguage(ActionEvent actionEvent){
        Locale.setDefault(new Locale("en", "US"));
    }






}
