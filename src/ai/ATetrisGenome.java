package ai;

import game.Board;
import game.Shape;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public abstract class ATetrisGenome<T>  {
    private float mutateRate;
    private float mutateStep;
    private float[] weights;
    private Random rand;
    public int fitness = Integer.MIN_VALUE;

    /**
     * Creates a new genome.
     * @param mutateRate the probability a chromosome weight will change.
     * @param mutateStep the +/- difference that can occur during a mutation.
     * @param seed a deterministic seed for the initial chromosome weights.
     */
    public ATetrisGenome(float mutateRate, float mutateStep, long seed) {
        this.mutateRate = mutateRate;
        this.mutateStep = mutateStep;
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
     * Uses the seed to deterministically mutate the genome.
     * If a mutation occurs then each of the chromosome weights
     * will randomly step forward or back.
     */
    public void maybeMutate() {
        if(rand.nextFloat() < mutateRate) {
            for (int i = 0; i < weights.length; i++) {
                fitness = Integer.MIN_VALUE;
                if (rand.nextBoolean()) {
                    weights[i] += mutateStep;
                } else {
                    weights[i] -= mutateStep;
                }
            }
        }
    }

    /**
     * Creates a new genome, the genome will randomly receive
     * chromosome weights from this genome or the partner genome.
     * @param partner genome that child may receive weights from
     * @return
     */
    public ATetrisGenome crossover(ATetrisGenome partner) {
        try {
            Constructor con = partner.getClass().getDeclaredConstructor(float.class, float.class, long.class);
            con.setAccessible(true);
            ATetrisGenome child = (ATetrisGenome) con.newInstance(mutateRate, mutateStep, rand.nextLong());
            for(int i = 0; i < weights.length; i++) {
                if(rand.nextBoolean()) {
                    child.weights[i] = partner.weights[i];
                }
                else {
                    child.weights[i] = this.weights[i];
                }
            }
            return child;
        } catch (Exception e) {}
        return null;
    }

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
