package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    static ArrayList<Tetramino> list = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ввведите высоту поля");
        int h = scanner.nextInt();
        System.out.println("Ввведите ширину поля");
        int w = scanner.nextInt();
        list.add(new Tetramino(new int[][]{{0, 1, 0},
                {1, 1, 1}}));
        list.add(new Tetramino(new int[][]{{0, 0, 1},
                {1, 1, 1}}));
        list.add(new Tetramino(new int[][] {{0, 1, 1},
                {1, 1, 0}}));
        list.add(new Tetramino(new int[][] {{1, 1, 1, 1}}));
        list.add(new Tetramino(new int[][] {{1, 1, 0},
                {0, 1, 1}}));
        list.add(new Tetramino(new int[][] {{1, 1},
                {1, 1}}));
        list.add(new Tetramino(new int[][] {{1, 0, 0},
                {1, 1, 1}}));
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("TetrisAI");
        primaryStage.setScene(new Scene(root, w * 30, h * 30));
        primaryStage.show();
        BestVariant bestVariant = new BestVariant(new int[h+2][w+2]);
        Field field = new Field(new int[h+2][w+2], new int[h+2][w+2], new int[h+2][w+2], new Label[h][w],
                bestVariant, h, w);
        Game game = new Game(root, field);
        game.go(list);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
