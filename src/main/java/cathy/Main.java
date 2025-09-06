package cathy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A GUI for Cathy using FXML.
 */
public class Main extends Application {

    private Cathy cathy = new Cathy("data/cathy.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(600);

            fxmlLoader.<MainWindow>getController().setCathy(cathy);  // inject the Cathy instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
