package memomax.gui;

import java.util.Objects;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import memomax.MemoMax;

/**
 * Controller for the main GUI of the MemoMax application.
 * Provides the layout for the other controls and manages the interaction between
 * the user interface and the MemoMax logic.
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

    private MemoMax memoMax;

    private final Image userImage = new Image(Objects.requireNonNull(this.getClass()
            .getResourceAsStream("/images/DaUser.png")));
    private final Image memoMaxImage = new Image(Objects.requireNonNull(this.getClass()
            .getResourceAsStream("/images/DaMemoMax.png")));
    private final Image memoMaxSadImage = new Image(Objects.requireNonNull(this.getClass()
            .getResourceAsStream("/images/MemoMaxSad.png")));

    /**
     * Initializes the controller after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty());

        String imageUrl = Objects.requireNonNull(getClass().getResource("/images/background.png"))
                .toExternalForm();
        scrollPane.setStyle("-fx-background-color: transparent; "
            + "-fx-background: transparent; "
            + "-fx-background-image: url('" + imageUrl + "'); "
            + "-fx-background-size: cover; "
            + "-fx-background-position: center; "
            + "-fx-background-repeat: no-repeat;");

        dialogContainer.setStyle("-fx-background-color: transparent;");

        userInput.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); "
                + "-fx-background-radius: 20; "
                + "-fx-border-color: #B0C4DE; "
                + "-fx-border-radius: 20; "
                + "-fx-border-width: 2; "
                + "-fx-padding: 5 15 5 15; "
                + "-fx-text-fill: #2F4F4F; "
                + "-fx-prompt-text-fill: #A9A9A9;");

        String buttonBaseStyle = "-fx-background-color: linear-gradient(to bottom, #E0F7FA, #B2EBF2); "
                + "-fx-background-radius: 20; "
                + "-fx-border-color: #81D4FA; "
                + "-fx-border-radius: 20; "
                + "-fx-border-width: 2; "
                + "-fx-text-fill: #01579B; "
                + "-fx-font-weight: bold; ";

        sendButton.setStyle(buttonBaseStyle);

        sendButton.setOnMouseEntered(e -> sendButton.setStyle(buttonBaseStyle
                + "-fx-effect: dropshadow(three-pass-box, rgba(255, 255, 255, 0.8), 15, 0, 0, 0); "
                + "-fx-background-color: linear-gradient(to bottom, #FFFFFF, #E0F7FA);"));
        sendButton.setOnMouseExited(e -> sendButton.setStyle(buttonBaseStyle));
    }

    /**
     * Sets the MemoMax logic instance for this window.
     * Also triggers the initial greeting message from MemoMax to be displayed in the GUI.
     *
     * @param m The MemoMax logic instance to be used by the GUI.
     */
    public void setMemoMax(MemoMax m) {
        memoMax = m;
        dialogContainer.getChildren().addAll(
                DialogBox.getMemoMaxDialog(memoMax.getGreeting(), memoMaxImage)
        );

        String startupError = memoMax.getStartupError();
        if (startupError != null) {
            dialogContainer.getChildren().add(
                    DialogBox.getMemoMaxDialog(startupError, memoMaxSadImage)
            );
        }

        scrollPane.requestFocus();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing MemoMax's reply.
     * Clears the user input after processing.
     * If the user input is "bye", the application will close after a short delay.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = memoMax.getResponse(input);

        Image botImageToUse = memoMax.isErrorResponse() ? memoMaxSadImage : memoMaxImage;

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMemoMaxDialog(response, botImageToUse)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
