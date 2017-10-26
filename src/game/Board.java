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

    public Board dropShape(Shape shape) {
        Matrix applied = applyShape(shape);
        while(collision(applied) == false) {
            applied.downShift();
        }
        return new Board(Matrix.add(this, applied),id+1);
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

    public boolean isGameOver() {
        return ( sumRow(0) > 0);
    }

}
