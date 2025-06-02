package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controllers.MainViewController;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start (Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/views/main-view.fxml"));
        Parent root = fxmlLoader.load();
        System.out.println("FXML načteno: " + (fxmlLoader.getLocation() != null));

        MainViewController controller = fxmlLoader.getController();
        System.out.println("Controller " + controller);

        Scene scene = new Scene(root);
        stage.setTitle("POKEMON");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}