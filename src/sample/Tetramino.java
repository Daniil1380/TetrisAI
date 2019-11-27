package sample;

import java.util.ArrayList;
import java.util.Random;

public class Tetramino {
    static ArrayList<int[][]> t = new ArrayList<>();



    static void createTetraminoCreator() {
        t.add(new int[][]{{0, 1, 0},
                {1, 1, 1}});
        t.add(new int[][]{{0, 0, 1},
                {1, 1, 1}});
        t.add(new int[][] {{0, 1, 1},
                {1, 1, 0}});
        t.add(new int[][] {{1, 1, 1, 1}});
        t.add(new int[][] {{1, 1, 0},
                {0, 1, 1}});
        t.add(new int[][] {{1, 1},
                {1, 1}});
        t.add(new int[][] {{1, 0, 0},
                {1, 1, 1}});
    }
    static int[][] create() {
        Random random = new Random();
        int num = random.nextInt(7);
        return t.get(num);
    }


    static int[][] rotate(int[][] in){
        int arrR[][] = new int [in[0].length][in.length];
        for (int i = 0; i < arrR.length; i++){
            for (int j = 0; j <arrR[i].length; j++){
                arrR[i][j] = in[in.length-j-1][i];
            }
        }
        return arrR;
    }






}
