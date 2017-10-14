package game;

import utils.Matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game {
    public Map<Character, Shape> shapes;
    public Board board;
    private Random rand;
    public final int width = 10;
    public final int height = 20;
    private Shape currentShape;
    private int score;

    public static void main(String[] args) {
        System.out.println("hello world");
    }

    public Game(){
        shapes = new HashMap<>();
        board = new Board(height, width, rand.nextInt());
        rand = new Random();
        addDefaultShapes();
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
                {3,0,0},
                {3,0,0},
                {3,3,0}
            });
        Shape J = new Shape (
            new int[][] {
                {0,0,4},
                {0,0,4},
                {0,4,4}
            });
        Shape Z = new Shape (
            new int[][] {
                {5,5,0},
                {0,5,5},
                {0,0,0}
            });
        Shape S = new Shape (
            new int[][] {
                {0,6,6},
                {6,6,0},
                {0,0,0}
            });
        shapes.put('I', I);
        shapes.put('O', O);
        shapes.put('L', L);
        shapes.put('J', J);
        shapes.put('Z', Z);
        shapes.put('S', S);
    }


    private void scoreCleared(int[] cleared) {
        for(int i = 0; i < cleared.length; i++) {
            if(cleared[i] > 0) {
                int sum = 0;
                int multiplier = 0;
                for(int j = i; j < cleared.length; i++, j++) {
                    if(cleared[j] > 0) {
                        sum += this.width;
                        multiplier++;
                    }
                    else {
                        break;
                    }
                }
                sum *= multiplier;
                this.score += sum;
            }
        }
    }

    //1. create all possible boards from current shape
    //2. feed boards to AI
    //3. AI pick move

}