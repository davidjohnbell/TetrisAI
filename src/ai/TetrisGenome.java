package ai;

import game.Board;

import java.util.Arrays;

public class TetrisGenome extends ATetrisGenome{
    /**
     * Creates a new genome.
     * @param mutateRate the probability a chromosome weight will change.
     * @param mutateStep the +/- difference that can occur during a mutation.
     * @param seed       a deterministic seed for the initial chromosome weights.
     */
    public TetrisGenome(float mutateRate, float mutateStep, long seed) {
        super(mutateRate, mutateStep, seed);
    }


    /**
     * Finds the tallest column on the board and calculates the height
     * of that column.
     * @param board the current board
     * @return the height of the tallest column
     */
    @SuppressWarnings("unused")
    private int maxHeightChromosome(Board board) {
        for(int i = 0; i < board.getHeight(); i++) {
            if(board.sumRow(i) > 0) {
                return board.getHeight() - i;
            }
        }
        return 0;
    }

    /**
     * Deermines the difference between the tallest column and the shortest column.
     * @param board the current board
     * @return the difference in height
     */
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

    /**
     * Counts the number of wholes in the board. A hole
     * is a zero value element that cant be directly filled
     * in by placing a piece.
     * @param board the current board
     * @return the number of holes
     */
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

    /**
     * Counts the number of non zero elements in the board.
     * @param board the current board
     * @return the number of occupied elements
     */
    @SuppressWarnings("unused")
    private int filledChromosome(Board board) {
        int count = 0;
        for(int i = 0; i < board.getHeight(); i++) {
            for(int j = 0; j < board.getWidth(); j++) {
                if(board.getElement(i, j) > 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Counts the number of rows that are completely
     * occupied by game pieces.
     * @param board the current board
     * @return the number of cleared rows
     */
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
