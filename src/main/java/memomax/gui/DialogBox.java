package memomax.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Controller for a custom DialogBox component.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a new DialogBox with the specified text and image.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img The image to be displayed as the profile picture.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
        dialog.setPadding(new javafx.geometry.Insets(10));
        dialog.setStyle("-fx-border-width: 0.5;");
        dialog.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a DialogBox representing the user's input.
     *
     * @param text The text input by the user.
     * @param img The user's profile image.
     * @return A DialogBox styled for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.dialog.setStyle(db.dialog.getStyle()
                + "-fx-background-color: #D1E8FF; "
                + "-fx-border-color: #000000; "
                + "-fx-text-fill: black; "
                + "-fx-background-radius: 15 0 15 15; "
                + "-fx-border-radius: 15 0 15 15;");
        return db;
    }

    /**
     * Creates a DialogBox representing MemoMax's response.
     * The dialog box is flipped so the image appears on the left.
     *
     * @param text The response text from MemoMax.
     * @param img MemoMax's profile image.
     * @return A DialogBox styled for MemoMax.
     */
    public static DialogBox getMemoMaxDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        db.dialog.setStyle(db.dialog.getStyle()
                + "-fx-background-color: #E8DAEF; "
                + "-fx-border-color: #000000; "
                + "-fx-text-fill: black; "
                + "-fx-background-radius: 0 15 15 15; "
                + "-fx-border-radius: 0 15 15 15;");
        return db;
    }
}
