package game;

import utils.Matrix;
import java.util.Arrays;

public class Board extends Matrix {
    public final int id;

    public Board(int rows, int columns, int id) {
        super(columns, rows);
        this.id = id;
    }

    public Board(Matrix boardMatrix, int id) {
        super(boardMatrix.getData());
        this.id = id;
    }

    public Matrix applyShape(Shape shape) {
        Matrix shapeMatrix = shape.getCurrent();
        shapeMatrix.resize(getWidth(), getHeight());
        for(int i = 0; i < shape.y; i++) {
            if(shapeMatrix.sumRow(shapeMatrix.getHeight() - 1) == 0) {
                shapeMatrix.downShift();
            }
        }
        for(int i = 0; i < shape.x; i++) {
            if(shapeMatrix.sumCol(shapeMatrix.getWidth() - 1) == 0) {
                shapeMatrix.rightShift();
            }
        }
        return shapeMatrix;
    }

    public boolean collision(Matrix appliedShape) {
        int rows = appliedShape.getHeight();
        int cols = appliedShape.getWidth();
        if(appliedShape.sumRow(rows-1) > 0) {
            return true;
        }
        for(int i = 0; i < rows - 1; i++) {
            for(int j = 0; j < cols; j++) {
                if(appliedShape.getElement(i, j) > 0
                && getElement(i+1, j) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] getFullRows() {
        int[] clearedIndexes = new int[getHeight()];
        for(int i = 0; i < getHeight(); i++) {
            if(sumRow(i) == 0) {
                clearedIndexes[i] = 1;
            }
        }
        return clearedIndexes;
    }

    public Board collapseRows(int[] clearedIndexes) {
        int rows = getHeight();
        int cols = getWidth();
        int[][] newData = new int[rows][cols];
        int last = rows - 1;
        for(int i = rows - 1; i > 0; i--) {
            if(clearedIndexes[i] == 0) {
                newData[last] =  Arrays.copyOf(getData()[i], cols);
                last--;
            }
        }
        return new Board(new Matrix(newData), id);
    }

    public static boolean win(Matrix board) {
        return (board.sumRow(0) > 0);
    }

}
