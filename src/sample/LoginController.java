package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public Hyperlink hyperlinkRegister;

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
}
