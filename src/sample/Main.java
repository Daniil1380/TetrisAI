package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("TetrisAI");
        primaryStage.setScene(new Scene(root, 360, 600));
        primaryStage.show();
        BestVariant bestVariant = new BestVariant();
        Field field = new Field(new int[22][12], new int[22][12], new int[22][12], new Label[20][10], bestVariant);
        Game game = new Game(root, field);
        game.go();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
