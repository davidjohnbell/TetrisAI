package game;

import ai.TetrisGenome;
import java.util.ArrayList;
import java.util.Random;


public class Game implements Runnable{
    private Board board;
    private Random rand;
    private final int width;
    private final int height;
    private ArrayList<Shape> shapes;
    private int score;
    public TetrisGenome genome;

    public Game(TetrisGenome genome, int width, int height, long seed){
        this.height  = height;
        this.width = width;
        this.genome = genome;
        shapes = new ArrayList<>();
        board = new Board(height, width, rand.nextInt());
        rand = new Random(seed);
        addDefaultShapes();
    }

    public void reset() {
        for(Shape shape : shapes) {
            shape.x = 0;
            shape.y = 0;
        }
        board = new Board(height, width, rand.nextInt());
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
        shapes.add(I);
        shapes.add(O);
        shapes.add(L);
        shapes.add(J);
        shapes.add(Z);
        shapes.add(S);
    }

    private void step() {
        Shape shape = shapes.get(rand.nextInt(shapes.size()));
        shape.x = 0;
        shape.y = 0;
        Board stepBoard = genome.makeMove(this.board, shape);
        int[] clearedRows = stepBoard.getFullRows();
        scoreCleared(clearedRows);
        stepBoard.collapseRows(clearedRows);
        this.board = stepBoard;
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

    @Override
    public void run() {
        score = 0;
        while(!board.isGameOver()) {
            step();
        }
        genome.fitness = score;
    }
}