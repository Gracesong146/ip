package cathy;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Cathy cathy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user_pfp.png"));
    private Image cathyImage = new Image(this.getClass().getResourceAsStream("/images/cathy_pfp.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Cathy instance */
    public void setCathy(Cathy c) {
        cathy = c;

        Image logo = new Image(this.getClass().getResourceAsStream("/images/cathy_welcome.png"));

        // Create an ImageView so we can resize
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(180);   // adjust size
        logoView.setFitHeight(180);  // adjust size
        logoView.setPreserveRatio(true);

        // Wrap inside a DialogBox-like container (or just add directly)
        HBox logoBox = new HBox(logoView);
        logoBox.setAlignment(Pos.CENTER_LEFT); // align left

        dialogContainer.getChildren().add(logoBox);

        dialogContainer.getChildren().add(
                DialogBox.getCathyDialog(cathy.welcomeMessage(), cathyImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Cathy's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = cathy.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getCathyDialog(response, cathyImage)
        );
        userInput.clear();
    }
}
