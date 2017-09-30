package game;

public class Board {
    private int[][] board;

    public Board(int rows, int columns) {
        board = new int[rows][columns];
    }

    public int[][] getBoard() {
        return board.clone();
    }

    public int[][] applyShape(Shape shape) {
        return null;
    }
}
