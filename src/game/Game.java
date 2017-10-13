package game;


import java.util.HashMap;
import java.util.Map;

public class Game {
    Map<Character, Shape> shapes;
    Board board;
    public final int width = 10;
    public final int height = 20;

    public static void main(String[] args) {
        System.out.println("hello world");
    }

    public Game(){
        shapes = new HashMap<>();

    }

    private void addDefaultShapes(){
        Shape I = new Shape(
            new int[][] {
                {1,1,1,1}
            });
        Shape O = new Shape (
            new int[][]{
                {2, 2},
                {2, 2}
            });
        Shape L = new Shape (
            new int[][] {
                {1,0,0},
                {1,0,0},
                {1,0,0}
            });
    }
}