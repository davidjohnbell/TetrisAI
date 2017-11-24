package game;

import java.util.Arrays;

public class Board extends Matrix {

    /**
     * Initializes a new board to the specified dimensions.
     * @param rows the number of rows
     * @param columns the number of columns
     */
    public Board(int rows, int columns) {
        super(columns, rows);
    }

    /**
     * Initializes a new board with the specified values.
     * @param boardMatrix the board data values
     */
    public Board(Matrix boardMatrix) {
        super(boardMatrix.getData());
    }

    public Board(Board board) {
        super(board.getData());
    }

    /**
     * Creates a blank matrix with the same dimensions as this board,
     * the location and rotation of the shape is translated onto the
     * board.
     * @return the matrix
     */
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

    /**
     * A collision is detected when an non zero element of the matrix
     * is directly above a non zero element of the board, or when
     * an element of the matrix occupies the last row of the board.
     */
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

    /**
     * The shape is translated onto the board and moves down until
     * a collision is detected or the end of the board is reached.
     * @param shape the shape to be dropped
     */
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

    /**
     * A full row contains no elements equal to zero. If row i
     * of the board is full then element i of the returned matrix
     * is equal to one, zero otherwise.
     * @return the array with indexes of full rows
     */
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

    /**
     * When a row i is collapsed all rows above it are shifted down.
     * The top row of the board will contain zeroes.
     * @param clearedIndexes an array with length equal to the height
     *                       of the board, and non zero values
     *                       representing which rows should be collapsed.
     */
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

    /**
     * A loss is detected when the top row of the board contains a non zero
     * value. The board height should account for spawning the shape.
     */
    public boolean isGameOver() {
        return ( sumRow(0) > 0);
    }

}
