package ai;

import game.Board;
import game.Shape;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TetrisGenome implements ITetrisGenome<TetrisGenome> {
    private float mutateRate;
    private float mutateStep;
    private float[] weights;
    private Random rand;
    public int fitness = Integer.MIN_VALUE;

    public TetrisGenome(float mutateRate, float mutateStep, long seed) {
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

    public void maybeMutate() {
        for(int i = 0; i < weights.length; i++) {
            if(rand.nextFloat() <= mutateRate) {
                fitness = Integer.MIN_VALUE;
                if(rand.nextBoolean()) {
                    weights[i] += mutateStep;
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

    @SuppressWarnings("unused")
    private int maxHeightChromosome(Board board) {
        for(int i = 0; i < board.getHeight(); i++) {
            if(board.sumRow(i) > 0) {
                return board.getHeight() - i;
            }
        }
        return 0;
    }

    @SuppressWarnings("unused")
    private int relativeHeightChromosome(Board board) {
        int[] net = new int[board.getWidth()];
        Arrays.fill(net, board.getHeight());
        for(int i = 0; i < board.getWidth(); i++) {
            for(int j = 0; j < board.getHeight(); j++) {
                int elem = board.getElement(j, i);
                if(elem > 0) {
                    break;
                }
                else {
                    net[i] = net[i] - 1;
                }
            }
        }
        int small = net[0], large = net[net.length-1];
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

    @SuppressWarnings("unused")
    private int holesChromosome(Board board) {
        int holes = 0;
        for(int i = 0; i < board.getWidth(); i++) {
            boolean start = false;
            for(int j = 0; j < board.getHeight(); i++) {
                int elem = board.getElement(j, i);
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

    @SuppressWarnings("unused")
    private int filledChromosome(Board board) {
        int empty = board.getWidth() * board.getHeight();
        for(int i = 0; i < board.getHeight(); i++) {
            for(int j = 0; j < board.getWidth(); j++) {
                if(board.getElement(i, j) > 0) {
                    empty--;
                }
            }
        }
        return empty;
    }

    @SuppressWarnings("unused")
    private int rowsClearedChromosome(Board board) {
        int[] cleared = board.getFullRows();
        int total = 0;
        for(int elem : cleared) {
            if(elem > 0) {
                total += 1;
            }
        }
        return total;
    }
}
