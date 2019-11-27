package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class Start {
    static int nowH = 0;
    static int nowW = 0;
    static long frame = 0;
    static int count = 0;
    static int Score = 0;
    static int minScore;
    static Label label = new Label();

    static void go(Pane pane) {
        label.setTranslateX(800);
        label.setTranslateY(800);
        pane.getChildren().add(label);
        Tetramino.createTetraminoCreator();
      Field.create(pane);
        Field.update();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                frame++;
                if (frame % 6 == 4) {
                    int n = Field.findFillLine();
                    Score += ((n * n) - (n -1)) * 100;
                    System.out.println("Score " + Score);

                    Field.update();
                }
                if (frame % 6 == 1) {
                    count++;
                    System.out.println(count);
                    label.setText(Integer.toString(count));
                    int[][] tetr = Tetramino.create();
                    for ( int k = 0; k < 4; k++ ) {
                        tetr = Tetramino.rotate(tetr);
                        for ( int z = 1; z < 12 - tetr[0].length; z++ ) {
                            nowW = 0;
                            nowH = 0;
                            for ( int t = 1; t < 22 - tetr.length; t++ ) {
                                if (Field.landing(z, t, tetr)) {
                                    if (t!=1) {
                                        nowH = t - 1;
                                        nowW = z;
                                    }
                                    break;
                                } else {
                                    nowH=t;
                                    nowW=z;
                                }
                            }
                            if (nowH != 0) {
                                Field.tryVariant(tetr, nowH, nowW);
                                if (Field.findFillLineVariant()==4) minScore-=10000;
                                minScore = 12 * (21-Field.findMaxHeight());
                                minScore += 8 * Field.findWell();
                                if (nowH < 7) minScore += 2 * (7-nowH);
                                minScore += 35 * Field.findHoles();
                                if (BestVariant.minScore > minScore) {
                                    BestVariant.minScore = minScore;
                                    BestVariant.h=nowH;
                                    BestVariant.w=nowW;
                                    BestVariant.figure=tetr;

                                }
                                Field.returnVFtoF();
                                Field.clear();
                            }

                        }
                    }
                    if (BestVariant.figure == null) stop();
                    else {
                        Field.setVariant(BestVariant.figure, BestVariant.h, BestVariant.w);
                        BestVariant.nuller();
                    }
                }
            }
        };
        timer.start();
    }


}
