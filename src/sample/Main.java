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
    private static ArrayList<Tetramino> list = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Пропустить настройку игры? Да - пропустить, Нет - настроить");
        String answer = scanner.next();
        int h = 20;
        int w = 10;
        int speed = 10;
        if (answer.equals("Нет")) {
        System.out.println("Ввведите высоту поля");
        h = scanner.nextInt();
        System.out.println("Ввведите ширину поля");
        w = scanner.nextInt();
        System.out.println("Введите скорость падения тетрамино (кадры в секунду)");
        speed = scanner.nextInt();
        }
        if (speed > 60) speed = 60;
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("TetrisAI");
        primaryStage.setScene(new Scene(root, w * 30, h * 30));
        primaryStage.setResizable(false);
        primaryStage.show();
        BestVariant bestVariant = new BestVariant();
        Field field = new Field(new boolean[h+2][w+2], new boolean[h+2][w+2], new boolean[h+2][w+2], new Label[h][w],
                bestVariant, h, w);

        list.add(new Tetramino(new boolean[][]{{false, true, false},
                {true, true, true}}));
        list.add(new Tetramino(new boolean[][]{{false, false, true},
                {true, true, true}}));
        list.add(new Tetramino(new boolean[][] {{false, true, true},
                {true, true, false}}));
        list.add(new Tetramino(new boolean[][] {{true, true, true, true}}));
        list.add(new Tetramino(new boolean[][] {{true, true, false},
                {false, true, true}}));
        list.add(new Tetramino(new boolean[][] {{true, true},
                {true, true}}));
        list.add(new Tetramino(new boolean[][] {{true, false,false},
                {true, true, true}}));

        Game game = new Game(root, field);
        game.go(list, speed);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
