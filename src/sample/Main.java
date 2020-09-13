package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/languages.fxml"), bundle);
        primaryStage.setTitle(bundle.getString("chooseLanguage"));
        primaryStage.setScene(new Scene(root, 150, 150));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
