package sample;

class BestVariant {
        private int h;
        private int w;
        private Tetramino figure;
        private int minScore  = Integer.MAX_VALUE;


        void clear() {
            this.h=0;
            this.w = 0;
            this.figure = null;
            this.minScore = Integer.MAX_VALUE;
        }
        int getH(){
            return this.h;
        }
        int getW(){
        return this.w;
         }
        Tetramino getFigure(){
            return this.figure;
        }

        private int findWell(Field field) {
            int answer=0;
            for ( int k = field.getH(); k > 1; k-- ) {
                for ( int z = 1; z < field.getW() + 1; z++ ) {
                    if (!field.getVariantField()[k][z] && field.getVariantField()[k][z - 1] &&
                            field.getVariantField()[k][z + 1] && !field.getVariantField()[k - 1][z]) {
                        answer +=1;
                    }
                }
            }
            return answer;
        }

        private int findHoles(Field field) {
            int answer=0;
            for ( int k = 2; k <= field.getH(); k++ ) {
                for ( int z = 1; z <= field.getW(); z++ ) {
                        if (!field.getVariantField()[k][z] && (field.getVariantField()[k - 1][z]
                                || field.getHoles()[k - 1][z])) {
                            field.getHoles()[k][z]=true;
                            answer ++;
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
                //these are not magic numbers. Just empirically calculated coefficients
                min += 12 * (field.getH() + 1 - this.findMaxHeight(field));
                min += 8 * this.findWell(field);
                if (nowH < 10) min += 3 * (10 - nowH);
                min += 38 * this.findHoles(field);
                if (this.minScore > min) {
                    this.minScore = min;
                    this.h = nowH;
                    this.w = nowW;
                    this.figure = tetr;
                }
            }
        }

        private int findMaxHeight(Field field) {
            for ( int k = 1; k < field.getH() + 1; k++ ) {
                for ( int z = 1; z < field.getW() + 1; z++ ) {
                    if (field.getVariantField()[k][z]) return k;
                }
            }
            return field.getVariantField().length - 1;
        }

        private void tryVariant(Tetramino tetr, int nowH, int nowW, Field field) {
            for ( int i = 0; i < tetr.massive.length; i++ ) {
                for ( int j = 0; j < tetr.massive[0].length; j++ ) {
                    if (tetr.massive[i][j]) {
                        field.getVariantField()[nowH + i][nowW + j] = true;
                        field.update();
                    }
                }
            }
        }
}
