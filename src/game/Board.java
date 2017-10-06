package game;

import utils.Matrix;
import java.util.Arrays;

public class Board {
    public Matrix board;
    public int score;
    public final int id;

    public Board(int rows, int columns, int id) {
        this.id = id;
        this.board = new Matrix(columns, rows);
    }

    public Board(Matrix board, int id) {
        this.id = id;
        this.board = board;
    }

    public static Matrix applyShape(Shape shape, Matrix board) {
        Matrix shapeData = shape.getCurrent();
        shapeData = shapeData.resize(board.getWidth(), board.getHeight());
        for(int i = 0; i < shape.y; i++) {
            if(shapeData.sumRow(shapeData.getHeight() - 1) == 0) {
                shapeData.downShift();
            }
        }
        for(int i = 0; i < shape.x; i++) {
            if(shapeData.sumCol(shapeData.getWidth() - 1) == 0) {
                shapeData.rightShift();
            }
        }
        return shapeData;
    }

    public static boolean collision(Matrix shape, Matrix board) {
        int rows = shape.getHeight();
        int cols = shape.getWidth();
        if(shape.sumRow(rows-1) > 0) {
            return true;
        }
        for(int i = 0; i < rows - 1; i++) {
            for(int j = 0; j < cols; j++) {
                if(shape.getElement(i, j) > 0
                && board.getElement(i+1, j) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int[] rowsCleared(Matrix board) {
        int rows = board.getHeight();
        int cols = board.getWidth();
        boolean cleared;
        int[] clearedIndexes = new int[rows];
        for(int i = 0; i < rows; i++) {
            cleared = true;
            for(int j = 0; j < cols; j++) {
                if(board.getElement(i, j) == 0) {
                    cleared = false;
                    clearedIndexes[i] = 0;
                    break;
                }
            }
            if(cleared) {
                clearedIndexes[i] = 1;
            }
        }
        collapseCleared(clearedIndexes, board);
        return clearedIndexes;
    }

    private static void collapseCleared(int[] clearedIndexes, Matrix board) {
        int rows = board.getHeight();
        int cols = board.getWidth();
        for(int i = 0; i < rows; i++) {
            if(clearedIndexes[i] > 0) {
                for(int j = 0; j < i; j++ ) {
                    board.getData()[j+1] = Arrays.copyOf(board.getData()[j], cols);
                }
                for(int j = 0; j < cols; j++) {
                    board.setElement(0, j, 0);
                }
            }
        }
    }

    public static boolean win(Matrix board) {
        return (board.sumRow(0) > 0);
    }



}
