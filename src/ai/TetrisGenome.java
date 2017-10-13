package ai;

import game.Board;
import utils.Matrix;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

public class TetrisGenome implements ITetrisGenome<TetrisGenome> {
    private float mutateRate;
    private float mutateStep;
    public float[] weights;
    private Random rand;

    public TetrisGenome(float mutateRate, float mutateStep, long seed) {
        this.mutateRate = mutateRate;
        this.mutateStep = mutateStep;
        this.rand = new Random(seed);
        initWeights();
    }

    public TetrisGenome() {
        this.mutateRate = 0.1f;
        this.mutateStep = 0.2f;
        this.rand = new Random();
        initWeights();
    }

    private void initWeights() {
        Method[] methods = this.getClass().getDeclaredMethods();
        int size = 0;
        for(Method method : methods) {
            if(method.getName().toLowerCase().contains("chromosome")) {
                size++;
            }
        }
        weights = new float[size];
        for(int i = 0; i < size; i++) {
            this.weights[i] = rand.nextFloat();
        }
    }

    public void mutate() {
        for(int i = 0; i < weights.length; i++) {
            if(rand.nextFloat() <= mutateRate) {
                if(rand.nextBoolean()) {
                    weights[i] += this.mutateStep;
                }
                else {
                    weights[i] -= mutateStep;
                }
            }
        }
    }

    public TetrisGenome crossover(TetrisGenome partner) {
        TetrisGenome child = new TetrisGenome(mutateRate, mutateStep, rand.nextLong());
        for(int i = 0; i < weights.length; i++) {
            if(rand.nextBoolean()) {
                child.weights[i] = partner.weights[i];
            }
            else {
                child.weights[i] = this.weights[i];
            }
        }
        return child;
    }


    public int evaluateBoard(Board board) {
        Method[] methods = this.getClass().getDeclaredMethods();
        int sum = 0;
        for(int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            method.setAccessible(true);
            if(method.getName().toLowerCase().contains("chromosome")) {
                try {
                    int methodScore = (int)method.invoke(this, board);
                    sum += Math.round((this.weights[i] * methodScore));
                }
                catch(Exception e){}
            }
        }
        return sum;
    }

    private int maxHeightChromosome(Board board) {
        Matrix matrix = board.board;
        for(int i = 0; i < matrix.getHeight(); i++) {
            if(matrix.sumRow(i) > 0) {
                return matrix.getHeight() - i;
            }
        }
        return 0;
    }

    private int relativeHeightChromosome(Board board) {
        Matrix matrix = board.board;
        int[] net = new int[matrix.getWidth()];
        Arrays.fill(net, matrix.getHeight());
        for(int i = 0; i < matrix.getWidth(); i++) {
            for(int j = 0; j < matrix.getHeight(); j++) {
                int elem = matrix.getElement(j, i);
                if(elem > 0) {
                    break;
                }
                net[i] = net[i] - 1;
            }
        }
        int large = net[0], small = net[0];
        for(int i = 1; i < net.length; i++) {
            if(net[i] > large) {
                large = net[i];
            }
            if(net[i] < small) {
                small = net[i];
            }
        }
        return large - small;
    }

    private int holesChromosome(Board board) {
        int holes = 0;
        Matrix matrix = board.board;
        for(int i = 0; i < matrix.getWidth(); i++) {
            boolean start = false;
            for(int j = 0; j < matrix.getHeight(); i++) {
                int elem = matrix.getElement(j, i);
                if(elem > 0) {
                    start = true;
                }
                else if(start) {
                    holes++;
                }
            }
        }
        return holes;
    }

    private int filledChromosome(Board board) {
        Matrix matrix = board.board;
        int empty = matrix.getWidth() * matrix.getHeight();
        for(int i = 0; i < matrix.getHeight(); i++) {
            for(int j = 0; j < matrix.getWidth(); j++) {
                if(matrix.getElement(i, j) > 0) {
                    empty--;
                }
            }
        }
        return empty;
    }

    private int rowsClearedChromosome(Board board) {
        int[] cleared = Board.rowsCleared(board.board);
        int total = 0;
        for(int elem : cleared) {
            if(elem > 0) {
                total += 1;
            }
        }
        return total;
    }
}
