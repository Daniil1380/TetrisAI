package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;


class Game {
    private static long frame = 0;
    Pane pane;
    Field field;

    Game(Pane pane, Field field){
        this.field=field;
        this.pane = pane;
    }

    void go() {
        Tetramino tetramino = new Tetramino();
        field.create(pane);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                frame++;
                if (frame % 6 == 0) {
                    field.checkAllVariants(tetramino.create());
                    if (field.hasBestVariant()) field.setVariant();
                    else { stop(); }
                    field.findFillLine();
                }
            }
        };
        timer.start();
    }
}
