package genomes;

import game.Board;
import game.Shape;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractGenome {
    protected float[] weights;
    protected Random rand;
    public int fitness = Integer.MIN_VALUE;

    /**
     * Creates a new genome.
     * @param seed a deterministic seed for the initial chromosome weights.
     */
    public AbstractGenome(long seed) {
        this.rand = new Random(seed);
        initWeights();
    }

    private void initWeights() {
        ArrayList<Method> chromosomes = getChromosomes();
        weights = new float[chromosomes.size()];
        for(int i = 0; i < weights.length; i++) {
            this.weights[i] = rand.nextFloat();
        }
    }

    /**
     * Changes the weights in some way such that the genome
     * will value some or maybe all of the chromosomes
     * differently. Mutating should reset the fitness.
     */
    public abstract void mutate();

    /**
     * Breeds a new genome from this genome and the partner.
     * @param partner the partner genome
     * @return the child genome
     */
    public abstract AbstractGenome crossover(AbstractGenome partner);

    private ArrayList<Method> getChromosomes() {
        Method[] methods = this.getClass().getDeclaredMethods();
        ArrayList<Method> chromosomes = new ArrayList<>();
        for(Method method : methods) {
            if(method.getName().toLowerCase().contains("chromosome")) {
                chromosomes.add(method);
            }
        }
        return chromosomes;
    }

    /**
     * The genome runs the board through each chromosome, the
     * weights are applied to each of the results and the total
     * is added up.
     * @param board the board to run through chromosomes
     * @return the score achieved from chromosomes
     */
    public int evaluateBoard(Board board) {
        ArrayList<Method> chromosomes = getChromosomes();
        int i = 0;
        int sum = 0;
        for(Method chromosome : chromosomes) {
            chromosome.setAccessible(true);
            try {
                int methodScore = (int)chromosome.invoke(this, board);
                sum += Math.round((this.weights[i] * methodScore));
            }
            catch(Exception e){}
            i++;
        }
        return sum;
    }

    /**
     * Moves and rotates the shape to find all possible moves.
     * The genome scores each move and returns the board with
     * the optimal move based on the current chromosome weights.
     * @param board the current board
     * @param shape the current shape
     * @return
     */
    public Board makeMove(Board board, Shape shape) {
        ArrayList<Board> boards = makeBoards(board, shape);
        int maxScore = Integer.MIN_VALUE;
        Board maxBoard = boards.get(0);
        for(int i = 1; i < boards.size(); i++) {
            Board current = boards.get(i);
            int score = evaluateBoard(current);
            if(score > maxScore) {
                maxBoard = current;
                maxScore = score;
            }
        }
        return maxBoard;
    }

    private ArrayList<Board> makeBoards(Board board, Shape shape) {
        ArrayList<Board> boards = new ArrayList<>();
        for(int r = 0; r < 4; r++) {
            for(int i = 0; i < board.getWidth(); i++) {
                shape.x = i;
                shape.y = 0;
                Board copyBoard = new Board(board);
                copyBoard.dropShape(shape);
                boards.add(copyBoard);
            }
            shape.rotate(1);
        }
        return boards;
    }

    /**
     * Returns the string representation of the weights. The
     * weights represent how the genome values each
     * chromosome.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Weights: ");
        for(int i = 0; i < weights.length; i++) {
            builder.append(String.format("%s, ", weights[i]));
        }
        return builder.toString();
    }
}
