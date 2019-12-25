package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


class Game {
    private static long frame = 0;
    Pane pane;
    Field field;
    int c=0;

    Game(Pane pane, Field field){
        this.field=field;
        this.pane = pane;
    }

    void go(List list) {
        field.create(pane);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                frame++;
                if (frame % 6 == 1) {
                    c++;
                    System.out.println(c);
                    field.checkAllVariants((Tetramino)list.get(new Random().nextInt(7)));
                    if (field.hasBestVariant()) field.setVariant();
                    else { stop(); }
                }
                if (frame % 6 == 3) {
                    field.findFillLine(true);
                    field.bestVariant.findHoles(field);
                    field.update(true);

                }
            }
        };
        timer.start();
    }
}
