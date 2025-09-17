package cathy;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private static final double LOGO_SIZE = 180.0;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    private Cathy cathy;
    private final Image userImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/user_pfp.png")));
    private final Image cathyImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/cathy_pfp.png")));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Cathy instance
     */
    public void setCathy(Cathy c) {
        cathy = c;

        Image logo = new Image(
                Objects.requireNonNull(this.getClass().getResourceAsStream("/images/cathy_welcome.png")));

        // Create an ImageView so we can resize
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(LOGO_SIZE); // adjust size
        logoView.setFitHeight(LOGO_SIZE); // adjust size
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

        DialogBox user = DialogBox.getUserDialog(input, userImage);
        DialogBox bot  = DialogBox.getCathyDialog(response, cathyImage);

        // AI-ASSIST: ChatGPT (2025-09-17)
        // If the response starts with our error marker, style the bubble as an error.
        if (response != null && (response.startsWith("<ERROR>"))) {
            bot.getStyleClass().add("error");   // <-- this toggles the CSS block above
        }

        dialogContainer.getChildren().addAll(user, bot);
        userInput.clear();
    }
}
