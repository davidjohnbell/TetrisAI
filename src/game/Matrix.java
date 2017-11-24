package game;

import java.util.Arrays;

public class Matrix {
    private int[][] data;

    /**
     * Initialize a new matrix with zeroes.
     * @param width the number of columns
     * @param height the number of rows
     */
    public Matrix(int width, int height) {
        data = new int[height][width];
    }

    /**
     * Initializes a new matrix with the provided values.
     * @param data the provided values
     */
    public Matrix(int[][] data) {
        int height = data.length;
        int width = data[0].length;
        this.data = new int[height][width];
        for(int i = 0; i < height; i++) {
            this.data[i] = Arrays.copyOf(data[i], width);
        }
    }

    /**
     * Copy constructor, initializes by copying values from provided matrix.
     * @param matrix values to copy
     */
    public Matrix(Matrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        this.data = new int[height][width];
        int[][] data = matrix.data;
        for(int i = 0; i < height; i++) {
            this.data[i] = Arrays.copyOf(data[i], width);
        }
    }

    /**
     * Swaps the matrix's rows with its columns.
     */
    public void transpose() {
        int height = getHeight(), width = getWidth();
        int[][] inverse = new int[width][height];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                inverse[j][i] = data[i][j];
            }
        }
        this.data = inverse;
    }

    public void reverseRows() {
        for(int[] array : data) {
            reverse(array);
        }
    }

    private void reverse(int[] array) {
        int start = 0, stop = array.length - 1;
        while(start < stop) {
            int temp = array[start];
            array[start] = array[stop];
            array[stop] = temp;
            start++;
            stop--;
        }
    }

    public void reverseColumns() {
        int width = getWidth(), height = getHeight();
        for(int i = 0; i < width; i++) {
            int start = 0, stop = height - 1;
            while(start < stop) {
                int temp = data[start][i];
                data[start][i] = data[stop][i];
                data[stop][i] = temp;
                start++;
                stop--;
            }
        }
    }

    /**
     * Rotate right n number of times.
     * @param n number of rotations
     */
    public void rotateRight(int n) {
        for(int i = 0; i < n; i++) {
            transpose();
            reverseRows();
        }
    }

    /**
     * Rotate left n number of times.
     * @param n number of rotations
     */
    public void rotateLeft(int n) {
        for(int i = 0; i < n; i++) {
            transpose();
            reverseColumns();
        }
    }

    /**
     * Adds two matrices, matrix B must be greater or equal in both height and width to that of A.
     */
    public static Matrix add(Matrix A, Matrix B) {
        int width = A.getWidth();
        int height = A.getHeight();
        Matrix C = new Matrix(width, height);
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                C.setElement(i, j, A.getElement(i, j)
                        + B.getElement(i, j));
            }
        }
        return C;
    }

    /**
     * Resizes the matrix. If the new matrix is larger than the new elements will be initialized to zero.
     * @param width number of columns
     * @param height number of rows
     */
    public void resize(int width, int height) {
        int[][] data = new int[height][width];
        int rows = Math.min(height, getHeight());
        int columns = Math.min(width, getWidth());
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = this.data[i][j];
            }
        }
        this.data = data;
    }

    /**
     * Sums each element of the row.
     * @param row zero indexed row number
     * @return the sum of that row
     */
    public int sumRow(int row) {
        int sum = 0;
        for(int value : this.data[row]) {
            sum += value;
        }
        return sum;
    }

    /**
     * Sums each element of the column.
     * @param col zero indexed column number
     * @return the sum of that row
     */
    public int sumCol(int col) {
        int sum = 0;
        for(int[] array : this.data) {
            sum += array[col];
        }
        return sum;
    }

    /**
     * Shifts every element left by one spot, the last column will contain zeroes after.
     */
    public void leftShift() {
        int rows = getHeight();
        int cols = getWidth();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols - 1; j++) {
                this.data[i][j] = this.data[i][j+1];
            }
            this.data[i][cols-1] = 0;
        }
    }

    /**
     * Shifts every element right by one spot, the first column will contain zeroes after.
     */
    public void rightShift() {
        int rows = getHeight();
        int cols = getWidth();
        for(int i = 0; i < rows; i++) {
            for(int j = cols - 1; j > 0; j--) {
                this.data[i][j] = this.data[i][j-1];
            }
            this.data[i][0] = 0;
        }
    }

    /**
     * Shifts every element up one spot, the last row will contain zeroes.
     */
    public void upShift() {
        int rows = getHeight();
        int cols = getWidth();
        for(int i = 0; i < rows - 1; i++) {
            this.data[i] = Arrays.copyOf(data[i+1], cols);
        }
        Arrays.fill(this.data[rows-1], 0);
    }

    /**
     * Shifts every element down one, the first row will contain zeroes after.
     */
    public void downShift() {
        int rows = getHeight();
        int cols = getWidth();
        for(int i = rows - 1; i > 0; i--) {
            this.data[i] = Arrays.copyOf(this.data[i-1], cols);
        }
        Arrays.fill(this.data[0], 0);
    }

    /**
     * Two obj are equal if and only if they are both instances of Matrix and all elements are equal in value.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix))
            return false;
        if (obj == this)
            return true;

        Matrix matrix = (Matrix) obj;
        int[][] matrixData = matrix.getData();
        if(getWidth() != matrix.getWidth() || getHeight() != matrix.getHeight())
            return false;
        for(int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if(this.data[i][j] != matrixData[i][j])
                    return false;
            }
        }
        return true;
    }


    public int getWidth() { return this.data[0].length;}
    public int getHeight() { return this.data.length;}

    /**
     * @return a reference to the internal 2D array used to store the matrix values.
     */
    public int[][] getData(){ return this.data;}
    public int getElement(int row, int column) { return this.data[row][column];}
    public void setElement(int row, int column, int value) { this.data[row][column] = value;}
    public void setData(int[][] data) {
        this.data = data;
    }
}
