package sample;

public class BestVariant {
    static int h;
    static int w;
    static int[][] figure;
    static int closedPixels;
    static int halfClosedPixels;
    static int maxHeight;
    static int well;
    static  int minScore  = 2147483647;

    static void nuller() {
        h=0;
        w = 0;
        figure = null;
        closedPixels = 0;
        halfClosedPixels = 0;
        maxHeight = 0;
        well = 0;
        minScore = 2147483647;

    }
}
