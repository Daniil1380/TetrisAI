package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.util.Arrays;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(new Scene(root, 360, 600));
        primaryStage.show();
        Start.go(root);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
