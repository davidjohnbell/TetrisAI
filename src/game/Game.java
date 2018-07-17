package game;

import genomes.AbstractGenome;
import java.util.ArrayList;
import java.util.Random;


public class Game implements Runnable{
    private Board board;
    private Random rand;
    private final int width = 10;
    private final int height = 23;
    private ArrayList<Shape> shapes;
    private int score;
    public AbstractGenome genome;

    /**
     *
     * @param genome the genome to play the game with
     * @param seed the seed used to spawn shapes
     */
    public Game(AbstractGenome genome, long seed){
        this.rand = new Random(seed);
        this.genome = genome;
        this.shapes = new ArrayList<>();
        this.board = new Board(height, width);
        addDefaultShapes();
    }

    /**
     * Iterates over the boards shapes and resets
     * their positions. A new board instance is
     * then created for a new game.
     */
    public void reset() {
        for(Shape shape : shapes) {
            shape.x = 0;
            shape.y = 0;
        }
        board = new Board(height, width);
    }

    /**
     * Default shapes are I, O, L, J, Z, S.
     */
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
                {0,5,5}
            });
        Shape S = new Shape (
            new int[][] {
                {0,6,6},
                {6,6,0}
            });
        shapes.add(I);
        shapes.add(O);
        shapes.add(L);
        shapes.add(J);
        shapes.add(Z);
        shapes.add(S);
    }

    /**
     * Choose a shape from the shape bag, reset its position,
     * and then feed the board and the shape to the genome.
     * The genome analyses the possible moves and scores
     * each move with the genomes chromosomes. The highest
     * scoring board is returned as output and the game
     * continues with that board.
     */
    public void step() {
        Shape shape = shapes.get(rand.nextInt(shapes.size()));
        shape.x = 0;
        shape.y = 0;
        Board stepBoard = genome.makeMove(this.board, shape);
        int[] clearedRows = stepBoard.getFullRows();
        scoreCleared(clearedRows);
        stepBoard.collapseRows(clearedRows);
        this.board = stepBoard;
    }

    /**
     * The score of a row is calculated by the width of the row.
     * A multiplier is used for consecutively full rows.
     * @param cleared an array with non zero values representing
     *                who's non zero elements represent full rows.
     */
    public void scoreCleared(int[] cleared) {
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

    /**
     * Sets the fitness of the genome by playing the game.
     * The fitness is equal to the score achieved in the game.
     * Only simulate a game if the genome doesn't have a
     * fitness score yet.
     */
    @Override
    public void run() {
        score = 0;
        while(!board.isGameOver()) {
            step();
            score++;
        }
        genome.fitness = score;
    }
}