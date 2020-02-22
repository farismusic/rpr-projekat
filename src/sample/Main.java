package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 250, 225));
        primaryStage.setResizable(false);
        primaryStage.show();

        LocalDateTime datumVrijeme = LocalDateTime.now();
        String d = datumVrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss"));
        System.out.println(d);

        String[] niz = d.split(" ");

        String[] vSS = niz[1].split(":");
        LocalTime vss1 = LocalTime.of(Integer.parseInt(vSS[0]), Integer.parseInt(vSS[1]), Integer.parseInt(vSS[2]));
        System.out.println(vss1);



    }


    public static void main(String[] args) {
        launch(args);
    }
}
