package utils;

public class Matrix {
    private int[][] data;


    public Matrix(int width, int height) {
        data = new int[height][width];
    }

    public Matrix(int[][] data) {
        this.data = data;
    }

    public void inverse() {
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
            int start = 0, stop = array.length;
            while(start < stop) {
                int temp = array[start];
                array[start] = array[stop];
                array[stop] = temp;
                start++;
                stop--;
            }
        }
    }

    public void reverseColumns() {
        int width = getWidth(), height = getHeight();
        for(int i = 0; i < width; i++) {
            int start = 0, stop = height;
            while(start < stop) {
                int temp = data[start][i];
                data[start][i] = data[stop][i];
                data[stop][i] = temp;
                start++;
                stop--;
            }
        }
    }

    public void rotateRight(int n) {
        for(int i = 0; i < n; i++) {
            inverse();
            reverseRows();
        }
    }

    public void rotateLeft(int n) {
        for(int i = 0; i < n; i++) {
            inverse();
            reverseColumns();
        }
    }

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

    public Matrix resize(int width, int height) {
        int[][] data = new int[height][width];
        int rows = Math.min(height, getHeight());
        int columns = Math.min(width, getWidth());
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                data[i][j] = this.data[i][j];
            }
        }
        return new Matrix(data);
    }

    public int getWidth() { return this.data[0].length;}
    public int getHeight() { return this.data.length;}
    public int[][] getData(){ return this.data;}
    public int getElement(int row, int column) { return this.data[row][column];}
    public void setElement(int row, int column, int value) { this.data[row][column] = value;}
    public void setData(int[][] data) {
        this.data = data;
    }
}
