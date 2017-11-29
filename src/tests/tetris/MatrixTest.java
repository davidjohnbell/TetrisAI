package tests.tetris;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import game.Matrix;

class MatrixTest {
    Matrix m1 = new Matrix(
        new int[][] {
            {1,2,3},
            {4,5,6},
            {7,8,9}});


    @Test
    void copyConstructor() {
        Matrix m2 = new Matrix(m1);
        Assertions.assertTrue(m1.equals(m2));
    }

    @Test
    void transpose() {
        Matrix m2 = new Matrix(
            new int[][] {
                {1,4,7},
                {2,5,8},
                {3,6,9}});
        m1.transpose();
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void reverseRows() {
        Matrix m2 = new Matrix(
            new int[][] {
                {3,2,1},
                {6,5,4},
                {9,8,7}});
        m1.reverseRows();
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void reverseColumns() {
        Matrix m2 = new Matrix(
            new int[][] {
                {7,8,9},
                {4,5,6},
                {1,2,3}});
        m1.reverseColumns();
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void rotateRight() {
        Matrix m2 = new Matrix(
            new int[][] {
                {7,4,1},
                {8,5,2},
                {9,6,3}});
        m1.rotateRight(1);
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void rotateLeft() {
        Matrix m2 = new Matrix(
            new int[][] {
                {3,6,9},
                {2,5,8},
                {1,4,7}});
        m1.rotateLeft(1);
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void add() {
        Matrix m2 = new Matrix(
            new int[][] {
                {0,0,0},
                {0,0,0},
                {0,0,0}});
        Matrix m3 = Matrix.add(m1,m2);
        Assertions.assertTrue(m3.equals(m1));
    }

    @Test
    void resize() {
        Matrix m2 = new Matrix(
            new int[][] {
                {1,2,3,0},
                {4,5,6,0},
                {7,8,9,0},
                {0,0,0,0}});
        m1.resize(4,4);
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void sumRow() {
        Assertions.assertEquals(m1.sumRow(0), 6);
        Assertions.assertEquals(m1.sumRow(1), 15);
        Assertions.assertEquals(m1.sumRow(2), 24);
    }

    @Test
    void sumCol() {
        Assertions.assertEquals(m1.sumCol(0), 12);
        Assertions.assertEquals(m1.sumCol(1), 15);
        Assertions.assertEquals(m1.sumCol(2), 18);
    }

    @Test
    void leftShift() {
        Matrix m2 = new Matrix(
            new int[][] {
                {2,3,0},
                {5,6,0},
                {8,9,0}});
        m1.leftShift();
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void rightShift() {
        Matrix m2 = new Matrix(
            new int[][] {
                {0,1,2},
                {0,4,5},
                {0,7,8}});
        m1.rightShift();
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void upShift() {
        Matrix m2 = new Matrix(
                new int[][] {
                        {4,5,6},
                        {7,8,9},
                        {0,0,0}});
        m1.upShift();
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void downShift() {
        Matrix m2 = new Matrix(
            new int[][] {
                {0,0,0},
                {1,2,3},
                {4,5,6}});
        m1.downShift();
        Assertions.assertTrue(m2.equals(m1));
    }

    @Test
    void getSetElement() {
        m1.setElement(2,2, 22);
        Assertions.assertEquals(m1.getElement(2,2), 22);
    }


}