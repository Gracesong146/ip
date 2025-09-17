package cathy;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Cathy using FXML.
 */
public class Main extends Application {
    private static final double MIN_HEIGHT = 220.0;
    private static final double MIN_WIDTH = 600.0;

    private Cathy cathy = new Cathy("data/cathy.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // Set window title
            stage.setTitle("Cathy - Your Underappreciated Task Assistant");

            // Set custom icon (for title bar + taskbar/dock)
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/cathy_pfp.png")));

            stage.setScene(scene);
            stage.setMinHeight(MIN_HEIGHT);
            stage.setMinWidth(MIN_WIDTH);

            fxmlLoader.<MainWindow>getController().setCathy(cathy); // inject the Cathy instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
