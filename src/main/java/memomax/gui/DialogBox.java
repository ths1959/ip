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
import javafx.scene.shape.Circle;

/**
 * Controller for a custom DialogBox component.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

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

        makeCircle();
    }

    private void makeCircle() {
        Circle clip = new Circle();
        clip.setCenterX(displayPicture.getFitWidth() / 2);
        clip.setCenterY(displayPicture.getFitHeight() / 2);
        clip.setRadius(displayPicture.getFitWidth() / 2);
        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box and sets alignment to CENTER_LEFT.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.CENTER_LEFT);
    }

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
