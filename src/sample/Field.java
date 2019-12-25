package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.Arrays;

class Field {
    int[][] field;
    int[][] variantField;
    int[][] holes;
    Label[][] labelField;
    BestVariant bestVariant;
    int h;
    int w;

    Field(int[][] field, int[][] variantField, int[][] holes, Label[][] labelField, BestVariant bestVariant,
          int h, int w){
        this.field =field;
        this.variantField = variantField;
        this.holes = holes;
        this.labelField = labelField;
        this.bestVariant=bestVariant;
        this.h = h;
        this.w =w;


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
                field[k][z]=0;
                variantField[k][z]=0;
            }
        }
        for ( int i = 0; i < w + 1; i++ ) {
            field[0][i] = 1;
            field[h + 1][i] = 1;
            variantField[0][i] = 1;
            variantField[h + 1][i] = 1;

        }
        for ( int j = 0; j < h + 2; j++ ) {
            field[j][0] = 1;
            field[j][w + 1] = 1;
            variantField[j][0] = 1;
            variantField[j][w + 1] = 1;

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

    void update(boolean showHoles) {
        for ( int k = 1; k < h + 1; k++ ) {
            for ( int z = 1; z < w + 1; z++ ) {
                if (field[k][z] == 1) {
                    labelField[k - 1][z - 1].setStyle(
                            "-fx-background-color: #ffc0cb"
                    );
                } else labelField[k - 1][z - 1].setStyle(
                        "-fx-background-color: #1d1d1d;"
                );

                if (bestVariant.holes[k][z]==1 && showHoles) {

                }
            }
        }
    }

    private void clearLine(int n, boolean isReal) {
        if (n < 1 || n > h) throw new NumberFormatException();
        int [][] abstractField;
        if (isReal) abstractField = field;
        else abstractField = variantField;
        for ( int i = n; i > 1; i-- ) {
            for ( int j = 0; j < w + 2; j++ ) {
                abstractField[i][j] = abstractField[i - 1][j];
            }
        }
        if (isReal) field = abstractField;
        else variantField = abstractField;
        this.update(false);
    }


    void setVariant() {
        for ( int i = 0; i < bestVariant.figure.massive.length; i++ ) {
            for ( int j = 0; j < bestVariant.figure.massive[0].length; j++ ) {
                if (bestVariant.figure.massive[i][j] == 1) {
                    this.field[bestVariant.h + i][bestVariant.w + j] = 1;
                    this.update(false);
                }
            }
        }
        bestVariant.clear();
    }

    void clear(){
        for ( int k = 1; k < h + 1; k++ ) {
            for ( int z = 1; z < w + 1; z++ ) {
                holes[k][z] = 0;
            }
            }

    }

    private  boolean landing(int w, int h, Tetramino tetramino) {
        for ( int i = 0; i < tetramino.massive.length; i++ ) {
            for ( int j = 0; j < tetramino.massive[0].length; j++ ) {
                if (tetramino.massive[i][j] == 1 && this.field[i + h][j + w] == 1) {
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
                    if (field[k][z] == 1) counter++;
                } else  if (variantField[k][z] == 1) counter++;
            }
            if (counter == w) {
                clearLine(k, isReal);
                n++;
            }
        }
        this.update(false);
        return n;
    }

    void checkAllVariants(Tetramino tetr){
        for ( int k = 0; k < 4; k++ ) {
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
        return bestVariant.figure != null;
    }

}
