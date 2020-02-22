package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 250, 225));
        primaryStage.setResizable(false);
        primaryStage.show();
        LocalDateTime datum = LocalDateTime.now();
        System.out.println(datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
