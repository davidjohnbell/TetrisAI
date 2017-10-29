package game;

import utils.Matrix;
import java.util.Arrays;

public class Board extends Matrix {
    public Board(int rows, int columns) {
        super(columns, rows);
    }

    public Board(Matrix boardMatrix) {
        super(boardMatrix.getData());
    }

    public Board(Board board) {
        super(board.getData());
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
                else if(appliedShape.getElement(i, j) > 0
                        && getElement(i, j) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dropShape(Shape shape) {
        Matrix applied = applyShape(shape);
        while(!collision(applied)) {
            applied.downShift();
        }
        for(int i = 0; i < getHeight(); i++) {
            for(int j = 0; j < getWidth(); j++) {
                int boardElem = getElement(i, j);
                int appliedElem = applied.getElement(i, j);
                setElement(i, j, appliedElem + boardElem);
            }
        }
    }

    public int[] getFullRows() {
        int height = getHeight();
        int width = getWidth();
        int[] clearedIndexes = new int[height];
        for(int i = 0; i < height; i++) {
            boolean cleared = true;
            for(int j = 0; j < width; j++) {
                if(getElement(i, j) == 0) {
                    cleared = false;
                    break;
                }
            }
            if(cleared) {
                clearedIndexes[i] = 1;
            }
        }
        return clearedIndexes;
    }

    public void collapseRows(int[] clearedIndexes) {
        int rows = getHeight();
        int cols = getWidth();
        int[][] prime = new int[rows][cols];
        int[][] old = getData();
        int last = rows - 1;
        for(int i = last; i >= 0; i--) {
            if(clearedIndexes[i] == 0) {
                prime[last] =  Arrays.copyOf(old[i], cols);
                last--;
            }
        }
        this.setData(prime);
    }

    public boolean isGameOver() {
        return ( sumRow(0) > 0);
    }

}
