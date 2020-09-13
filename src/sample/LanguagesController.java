package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguagesController {

    public Button btn;

    public void closeWindow(){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public void actionBosnian (ActionEvent actionEvent){
        Locale.setDefault(new Locale("bs", "BA"));
        closeWindow();
        otvoriLogin();
    }

    public void actionEnglish (ActionEvent actionEvent){
        Locale.setDefault(new Locale("en", "US"));
        closeWindow();
        otvoriLogin();
    }

    public void otvoriLogin(){
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
            root = loader.load();
            stage.setTitle(bundle.getString("login"));
            stage.setScene(new Scene(root, 250, 225));
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
