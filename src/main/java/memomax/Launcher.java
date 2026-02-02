package memomax;

import javafx.application.Application;

/**
 * Acts as the entry point for the MemoMax application.
 * This class is used to launch the JavaFX GUI to bypass classpath limitations.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}