package sample;

import java.util.Arrays;

public class BestVariant {
        int h;
        int w;
        Tetramino figure;
        int[][] holes;
        private int minScore  = Integer.MAX_VALUE;
        int hhh;

        BestVariant(int[][] holes){
            this.holes = holes;
        }

        void clear() {
            this.h=0;
            this.w = 0;
            this.figure = null;
            this.minScore = Integer.MAX_VALUE;
        }

        private int findWell(Field field) {
            int answer=0;
            for ( int k = field.h; k > 1; k-- ) {
                for ( int z = 1; z < field.w + 1; z++ ) {
                    if (field.variantField[k][z]==0 && field.variantField[k][z-1]==1 && field.variantField[k][z+1]==1
                            && field.variantField[k-1][z]==0) {
                        answer +=1;
                    }
                }
            }
            return answer;
        }

        int findHoles(Field field) {
            int answer=0;
            for ( int k = 2; k <= field.h; k++ ) {
                for ( int z = 1; z <= field.w; z++ ) {
                        if (field.variantField[k][z]==0 && (field.variantField[k-1][z]==1 || field.holes[k-1][z]==1)) {
                            field.holes[k][z]=1;
                            answer ++;
                        }

                }
            }
            return answer;
        }

    private int findMinHeight(Field field) {
        int answer=0;
        for ( int k = 2; k < field.h + 1; k++ ) {
            for ( int z = 1; z < field.w + 1; z++ ) {
                if (field.variantField[k][z]==0 && (field.holes[k-1][z]==1)) {
                    answer = k;
                }
            }
        }
        return answer;
    }

        void checkVariant(Tetramino tetr, int nowH, int nowW, Field field){
            field.returnVFtoF();
            field.clear();
            if (nowH != 0 && nowW != 0) {
                int min = 0;
                int lines;
                this.tryVariant(tetr, nowH, nowW, field);
                if ((lines = field.findFillLine(false)) > 0) min -= (int)Math.pow(10, lines);
                min += 12 * (field.h + 1 - this.findMaxHeight(field));
                min += 8 * this.findWell(field);
                if (nowH < 10) min += 3 * (10 - nowH);
                min += 45 * this.findHoles(field);
                System.out.println(this.findMinHeight(field));
                if (this.minScore > min) {
                    this.minScore = min;
                    this.h = nowH;
                    this.w = nowW;
                    this.figure = tetr;
                    this.hhh = this.findHoles(field);
                    this.holes = field.holes;
                }
            }
        }

        private int findMaxHeight(Field field) {
            for ( int k = 1; k < field.h + 1; k++ ) {
                for ( int z = 1; z < field.w + 1; z++ ) {
                    if (field.variantField[k][z] == 1) return k;
                }
            }
            return field.variantField.length - 1;
        }

        private void tryVariant(Tetramino tetr, int nowH, int nowW, Field field) {
            for ( int i = 0; i < tetr.massive.length; i++ ) {
                for ( int j = 0; j < tetr.massive[0].length; j++ ) {
                    if (tetr.massive[i][j] == 1) {
                        field.variantField[nowH + i][nowW + j] = 1;
                        field.update(false);
                    }
                }
            }
        }
}
