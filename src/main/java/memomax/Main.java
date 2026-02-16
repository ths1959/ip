package memomax;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import memomax.gui.MainWindow;

/**
 * A GUI for MemoMax using FXML.
 */
public class Main extends Application {

    private final MemoMax memoMax = new MemoMax();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("MemoMax");
            stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass()
                    .getResourceAsStream("/images/DaMemoMax.png"))));

            fxmlLoader.<MainWindow>getController().setMemoMax(memoMax);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
