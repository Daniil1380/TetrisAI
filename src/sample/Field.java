package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
class Field {
    private boolean[][] field;
    private Label[][] labelField;
    private boolean[][] variantField;
    private boolean[][] holes;
    private BestVariant bestVariant;
    private int h;
    private int w;

    Field(boolean[][] field, boolean[][] variantField, boolean[][] holes, Label[][] labelField, BestVariant bestVariant,
          int h, int w){
        this.field =field;
        this.variantField = variantField;
        this.holes = holes;
        this.labelField = labelField;
        this.bestVariant=bestVariant;
        this.h = h;
        this.w =w;
    }



    boolean[][] getHoles(){
        return this.holes;
    }

    boolean[][] getVariantField(){
        return this.variantField;
    }

    int getH(){
        return this.h;
    }

    int getW(){
        return this.w;
    }

    void returnVFtoF() {
        for ( int i = 0; i < field[0].length; i++ ) {
            for ( int j = 0; j < field.length; j++ ) {
                variantField[j][i] = field[j][i];
            }
        }
    }

    void create(Pane pane) {
        for (int k = 1; k < h +1; k++) {
            for ( int z = 1; z < w+1; z++ ) {
                field[k][z]=false;
                variantField[k][z]=false;
            }
        }
        for ( int i = 0; i < w + 2; i++ ) {
            field[0][i] = true;
            field[h + 1][i] = true;
            variantField[0][i] = true;
            variantField[h + 1][i] = true;
        }
        for ( int j = 0; j < h + 2; j++ ) {
            field[j][0] = true;
            field[j][w + 1] = true;
            variantField[j][0] = true;
            variantField[j][w + 1] = true;
        }
        for ( int k = 0; k < h; k++ ) {
            for ( int z = 0; z < w; z++ ) {
                labelField[k][z] = new Label();
                labelField[k][z].setMinHeight(30);
                labelField[k][z].setMinWidth(30);
                labelField[k][z].setTranslateY(k * 30 - h * 15 + 15);
                labelField[k][z].setTranslateX(z * 30 - w * 15 + 15);
                labelField[k][z].setStyle(
                        "-fx-background-color: #1d1d1d;"
                );
                pane.getChildren().add(labelField[k][z]);
            }
        }

    }

    void update() {
        for ( int k = 1; k < h + 1; k++ ) {
            for ( int z = 1; z < w + 1; z++ ) {
                if (field[k][z]) {
                    labelField[k - 1][z - 1].setStyle(
                            "-fx-background-color: #ffc0cb; -fx-border-color: #ffffff;"
                    );
                } else labelField[k - 1][z - 1].setStyle(
                        "-fx-background-color: #1d1d1d;"
                );
            }
        }
    }

    private void clearLine(int n, boolean isReal) {
        if (n < 1 || n > h) throw new NumberFormatException();
        boolean [][] abstractField;
        if (isReal) abstractField = field;
        else abstractField = variantField;
        for ( int i = n; i > 1; i-- ) {
            if (w + 2 >= 0) System.arraycopy(abstractField[i - 1], 0, abstractField[i], 0, w + 2);
        }
        if (isReal) field = abstractField;
        else variantField = abstractField;
        this.update();
    }


    void setVariant() {
        for ( int i = 0; i < bestVariant.getFigure().massive.length; i++ ) {
            for ( int j = 0; j < bestVariant.getFigure().massive[0].length; j++ ) {
                if (bestVariant.getFigure().massive[i][j]) {
                    this.field[bestVariant.getH() + i][bestVariant.getW() + j] = true;
                    this.update();
                }
            }
        }
        bestVariant.clear();
    }

    void clear(){
        for ( int k = 1; k < h + 1; k++ ) {
            for ( int z = 1; z < w + 1; z++ ) {
                holes[k][z] = false;
            }
            }

    }

    private  boolean landing(int w, int h, Tetramino tetramino) {
        for ( int i = 0; i < tetramino.massive.length; i++ ) {
            for ( int j = 0; j < tetramino.massive[0].length; j++ ) {
                if (tetramino.massive[i][j] && this.field[i + h][j + w]) {
                    return true;
                }
            }
        }
        return false;
    }

    int findFillLine(boolean isReal){
        int n = 0;
        for ( int k = 1; k < h + 1; k++ ) {
            int counter = 0;
            for ( int z = 1; z < w + 1; z++ ) {
                if (isReal) {
                    if (field[k][z]) counter++;
                } else  if (variantField[k][z]) counter++;
            }
            if (counter == w) {
                clearLine(k, isReal);
                n++;
            }
        }
        this.update();
        return n;
    }

    void checkAllVariants(Tetramino tetr){
        for ( int k = 0; k < 4; k++ ) { //4 sides
            tetr=tetr.rotate();
            for ( int z = 1; z < w + 2 - tetr.massive[0].length; z++ ) {
                int nowW = 0;
                int nowH = 0;
                for ( int t = 1; t < h + 2 - tetr.massive.length; t++ ) {
                    if (this.landing(z, t, tetr)) {
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
                bestVariant.checkVariant(tetr, nowH, nowW, this);
            }
        }
    }
    boolean hasBestVariant(){
        return bestVariant.getFigure() != null;
    }
}
