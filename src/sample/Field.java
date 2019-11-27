package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class Field {
    static int[][] field = new int[22][12];
    static int[][] variusField = new int[22][12];
    static int[][] well = new int[22][12];
    static int[][] holes = new int[22][12];
    static Label[][] labelField = new Label[20][10];


    public static void returnVFtoF() {
        for ( int i = 0; i < field[0].length; i++ ) {
            for ( int j = 0; j < field.length; j++ ) {
                variusField[j][i] = field[j][i];
            }
        }
    }

    public static void create(Pane pane) {
        for (int k = 1; k < 21; k++) {
            for ( int z = 1; z < 11; z++ ) {
                field[k][z]=0;
                variusField[k][z]=0;
            }
        }
        for ( int i = 0; i < 12; i++ ) {
            field[0][i] = 1;
            field[21][i] = 1;
            variusField[0][i] = 1;
            variusField[21][i] = 1;

        }
        for ( int j = 0; j < 22; j++ ) {
            field[j][0] = 1;
            field[j][11] = 1;
            variusField[j][0] = 1;
            variusField[j][11] = 1;

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

    public static void update() {
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

    public static void clearLineVariant(int n) {
        if (n < 1 || n > 20) throw new NumberFormatException();
        for ( int i = n; i > 1; i-- ) {
            for ( int j = 0; j < 12; j++ ) {
                variusField[i][j] = variusField[i - 1][j];
            }

        }
    }

    public static void clearLine(int n) {
        if (n < 1 || n > 20) throw new NumberFormatException();
        for ( int i = n; i > 1; i-- ) {
            for ( int j = 0; j < 12; j++ ) {
                field[i][j] = field[i - 1][j];
            }

        }
    }

    public static boolean landing(int w, int h, int[][] tetramino) {
        for ( int i = 0; i < tetramino.length; i++ ) {
            for ( int j = 0; j < tetramino[0].length; j++ ) {
                if (tetramino[i][j] == 1 && Field.field[i + h][j + w] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void tryVariant(int[][] tetr, int nowH, int nowW) {
        for ( int i = 0; i < tetr.length; i++ ) {
            for ( int j = 0; j < tetr[0].length; j++ ) {
                if (tetr[i][j] == 1) {
                    Field.variusField[nowH + i][nowW + j] = 1;
                    Field.update();
                }
            }
        }
    }

    public static int findMaxHeight() {
        for ( int k = 1; k < 21; k++ ) {
            for ( int z = 1; z < 11; z++ ) {
                if (variusField[k][z] == 1) return k;
            }

        }
        return 21;
    }

    public static void setVariant(int[][] tetr, int nowH, int nowW) {
        for ( int i = 0; i < tetr.length; i++ ) {
            for ( int j = 0; j < tetr[0].length; j++ ) {
                if (tetr[i][j] == 1) {
                    Field.field[nowH + i][nowW + j] = 1;
                    Field.update();
                }
            }
        }
    }
    public static int findWell() {
        int answer=0;
        for ( int k = 20; k > 1; k-- ) {
            for ( int z = 1; z < 11; z++ ) {
                if (variusField[k][z]==0 && variusField[k][z-1]==1 && variusField[k][z+1]==1
                 && variusField[k-1][z]==0) {
                    well[k][z]=1;
                    answer +=1;
                }
            }
            }
            return answer;
    }

    public static int findHoles() {
        int answer=0;
        for ( int k = 2; k < 21; k++ ) {
            for ( int z = 1; z < 11; z++ ) {
                if (variusField[k][z]==0 && (variusField[k-1][z]==1 || holes[k-1][z]==1)) {
                    holes[k][z]=1;
                    answer ++;
                }
            }
        }
        return answer;
    }

    static void clear(){
        for ( int k = 1; k < 21; k++ ) {
            for ( int z = 1; z < 11; z++ ) {
                well[k][z] = 0;
                holes[k][z] = 0;
            }
            }

    }
    static int findFillLineVariant(){
        int answer = 0;
        for ( int k = 1; k < 21; k++ ) {
            int counter = 0;
            for ( int z = 1; z < 11; z++ ) {
                if (variusField[k][z]==1) counter++;
            }
            if (counter == 10) {
                clearLineVariant(k);
                k=1;
                answer++;
            }
            }
            return answer;
        }

    static int findFillLine(){
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
        return n;
    }
    public static int findHorizontalTransition() {
        int answer=0;
        for ( int k = 1; k < 21; k++ ) {
            for ( int z = 1; z < 9; z++ ) {
                if (variusField[k][z]!=variusField[k][z+1]) {
                    answer ++;
                }
            }
        }
        return answer;
    }

    public static int findVerticalTransition() {
        int answer=0;
            for ( int z = 1; z < 11; z++ ) {
                for ( int k = 1; k < 19; k++ ) {
                if (variusField[k][z]!=variusField[k+1][z]) {
                    answer ++;
                }
            }
        }
        return answer;
    }

}
