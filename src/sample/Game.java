package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import java.util.List;
import java.util.Random;
import java.util.Stack;


class Game {
    private long frame = 0;
    private Pane pane;
    private Field field;
    private int count = 0;

    Game(Pane pane, Field field){
        this.field=field;
        this.pane = pane;
    }

    void go(List list, int speed, int size) {
        Stack<Tetramino> stack = new Stack<>();
        stack.push((Tetramino) list.get(new Random().nextInt(list.size())));
        field.create(pane, size);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                frame++;
                if (frame % (60 / speed) == 0) {
                    count++;
                    System.out.println(count);
                    stack.push((Tetramino) list.get(new Random().nextInt(list.size())));
                    field.checkAllVariants(stack.pop());
                    if (field.hasBestVariant()) field.setVariant();
                    else { stop(); }
                    field.findFillLine(true);
                }
            }
        };
        timer.start();
    }
}
