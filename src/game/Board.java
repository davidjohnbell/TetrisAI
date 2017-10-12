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

    public static boolean collision(Matrix appliedShape, Matrix board) {
        int rows = appliedShape.getHeight();
        int cols = appliedShape.getWidth();
        if(appliedShape.sumRow(rows-1) > 0) {
            return true;
        }
        for(int i = 0; i < rows - 1; i++) {
            for(int j = 0; j < cols; j++) {
                if(appliedShape.getElement(i, j) > 0
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
        int[][] newData = new int[rows][cols];
        int last = rows - 1;
        for(int i = rows - 1; i > 0; i--) {
            if(clearedIndexes[i] == 0) {
                newData[last] =  Arrays.copyOf(board.getData()[i], cols);
                last--;
            }
        }
        board.setData(newData);
    }

    public static boolean win(Matrix board) {
        return (board.sumRow(0) > 0);
    }

}
