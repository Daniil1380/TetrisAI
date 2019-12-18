package sample;

public class BestVariant {
        int h;
        int w;
        int[][] figure;
        private int minScore  = Integer.MAX_VALUE;

        void clear() {
            this.h=0;
            this.w = 0;
            this.figure = null;
            this.minScore = Integer.MAX_VALUE;
        }

        private int findWell(Field field) {
            int answer=0;
            for ( int k = 20; k > 1; k-- ) {
                for ( int z = 1; z < 11; z++ ) {
                    if (field.variantField[k][z]==0 && field.variantField[k][z-1]==1 && field.variantField[k][z+1]==1
                            && field.variantField[k-1][z]==0) {
                        answer +=1;
                    }
                }
            }
            return answer;
        }

        private int findHoles(Field field) {
            int answer=0;
            for ( int k = 2; k < 21; k++ ) {
                for ( int z = 1; z < 11; z++ ) {
                    if (field.variantField[k][z]==0 && (field.variantField[k-1][z]==1 || field.holes[k-1][z]==1)) {
                        field.holes[k][z]=1;
                        answer ++;
                    }
                }
            }
            return answer;
        }

        void checkVariant(int[][] tetr, int nowH, int nowW, Field field){
            if (nowH != 0 && nowW != 0) {
                int min;
                this.tryVariant(tetr, nowH, nowW, field);
                min = 12 * (21 - this.findMaxHeight(field));
                if (field.findFillLineVariant() == 4) min -= 10000;
                min += 8 * this.findWell(field);
                if (nowH < 7) min += 2 * (7 - nowH);
                min += 35 * this.findHoles(field);
                if (this.minScore > min) {
                    this.minScore = min;
                    this.h = nowH;
                    this.w = nowW;
                    this.figure = tetr;
                }
                field.returnVFtoF();
                field.clear();
            }
        }

        private int findMaxHeight(Field field) {
            for ( int k = 1; k < 21; k++ ) {
                for ( int z = 1; z < 11; z++ ) {
                    if (field.variantField[k][z] == 1) return k;
                }
            }
            return field.variantField.length - 1;
        }

        private void tryVariant(int[][] tetr, int nowH, int nowW, Field field) {
            for ( int i = 0; i < tetr.length; i++ ) {
                for ( int j = 0; j < tetr[0].length; j++ ) {
                    if (tetr[i][j] == 1) {
                        field.variantField[nowH + i][nowW + j] = 1;
                        field.update();
                    }
                }
            }
        }
}
