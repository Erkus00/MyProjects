package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Trabajo realizado por:
 * Carlos Aragón García
 * Arturo Guzmán Lucena
 */

public class PrincipalApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("/Principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 700);
        stage.setTitle("Hello!");
        String css = this.getClass().getResource("/PrincipalCSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        PrincipalController controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}