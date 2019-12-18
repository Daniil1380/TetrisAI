package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

class Field {
    int[][] field;
    int[][] variantField;
    int[][] holes;
    private Label[][] labelField;
    private BestVariant bestVariant;

    Field(int[][] field, int[][] variantField, int[][] holes, Label[][] labelField, BestVariant bestVariant){
        this.field = new int[22][12];
        this.variantField = new int[22][12];
        this.holes = new int[22][12];
        this.labelField = new Label[20][10];
        this.bestVariant=bestVariant;

    }


    void returnVFtoF() {
        for ( int i = 0; i < field[0].length; i++ ) {
            for ( int j = 0; j < field.length; j++ ) {
                variantField[j][i] = field[j][i];
            }
        }
    }

    void create(Pane pane) {
        for (int k = 1; k < 21; k++) {
            for ( int z = 1; z < 11; z++ ) {
                field[k][z]=0;
                variantField[k][z]=0;
            }
        }
        for ( int i = 0; i < 12; i++ ) {
            field[0][i] = 1;
            field[21][i] = 1;
            variantField[0][i] = 1;
            variantField[21][i] = 1;

        }
        for ( int j = 0; j < 22; j++ ) {
            field[j][0] = 1;
            field[j][11] = 1;
            variantField[j][0] = 1;
            variantField[j][11] = 1;

        }
        for ( int k = 0; k < 20; k++ ) {
            for ( int z = 0; z < 10; z++ ) {
                labelField[k][z] = new Label();
                labelField[k][z].setMinHeight(30);
                labelField[k][z].setMinWidth(30);
                labelField[k][z].setTranslateY(k * 30 - 360);
                labelField[k][z].setTranslateX(z * 30 - 160);
                labelField[k][z].setStyle(
                        "-fx-background-color: #1d1d1d;"
                );
                pane.getChildren().add(labelField[k][z]);
            }
        }

    }

    void update() {
        for ( int k = 1; k < 21; k++ ) {
            for ( int z = 1; z < 11; z++ ) {
                if (field[k][z] == 1) {
                    labelField[k - 1][z - 1].setStyle(
                            "-fx-background-color: #ffc0cb"
                    );
                } else labelField[k - 1][z - 1].setStyle(
                        "-fx-background-color: #1d1d1d;"
                );
            }
        }
    }

    private void clearLineVariant(int n) {
        if (n < 1 || n > 20) throw new NumberFormatException();
        for ( int i = n; i > 1; i-- ) {
            for ( int j = 0; j < 12; j++ ) {
                variantField[i][j] = variantField[i - 1][j];
            }

        }
    }

    private  void clearLine(int n) {
        if (n < 1 || n > 20) throw new NumberFormatException();
        for ( int i = n; i > 1; i-- ) {
            for ( int j = 0; j < 12; j++ ) {
                field[i][j] = field[i - 1][j];
            }

        }
    }

    void setVariant() {
        for ( int i = 0; i < bestVariant.figure.length; i++ ) {
            for ( int j = 0; j < bestVariant.figure[0].length; j++ ) {
                if (bestVariant.figure[i][j] == 1) {
                    this.field[bestVariant.h + i][bestVariant.w + j] = 1;
                    this.update();
                }
            }
        }
        bestVariant.clear();
    }

    void clear(){
        for ( int k = 1; k < 21; k++ ) {
            for ( int z = 1; z < 11; z++ ) {
                holes[k][z] = 0;
            }
            }

    }

    int findFillLineVariant(){
        int answer = 0;
        for ( int k = 1; k < 21; k++ ) {
            int counter = 0;
            for ( int z = 1; z < 11; z++ ) {
                if (variantField[k][z]==1) counter++;
            }
            if (counter == 10) {
                clearLineVariant(k);
                k=1;
                answer++;
            }
            }
            return answer;
        }

    private  boolean landing(int w, int h, int[][] tetramino) {
        for ( int i = 0; i < tetramino.length; i++ ) {
            for ( int j = 0; j < tetramino[0].length; j++ ) {
                if (tetramino[i][j] == 1 && this.field[i + h][j + w] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    int findFillLine(){
        int n = 0;
        for ( int k = 1; k < 21; k++ ) {
            int counter = 0;
            for ( int z = 1; z < 11; z++ ) {
                if (field[k][z]==1) counter++;
            }
            if (counter == 10) {
                clearLine(k);
                n++;
                k=1;
            }
        }
        this.update();
        return n;
    }

    void checkAllVariants(int[][] tetr){
        for ( int k = 0; k < 4; k++ ) {
            tetr = Tetramino.rotate(tetr);
            for ( int z = 1; z < 12 - tetr[0].length; z++ ) {
                 int nowW = 0;
                int nowH = 0;
                for ( int t = 1; t < 22 - tetr.length; t++ ) {
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
