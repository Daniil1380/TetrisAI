package sample;

class Tetramino {

    int[][] massive;

    Tetramino(int[][] massive){
        this.massive=massive;
    }

    Tetramino rotate(){
        int arrR[][] = new int [massive[0].length][massive.length];
        for (int i = 0; i < arrR.length; i++){
            for (int j = 0; j <arrR[i].length; j++){
                arrR[i][j] = massive[massive.length-j-1][i];
            }
        }
        return new Tetramino(arrR);
    }
}
