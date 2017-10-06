package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import utils.Matrix;

class MatrixTest {
    @org.junit.jupiter.api.Test
    void inverse() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {1,4,7},
                {2,5,8},
                {3,6,9}});
        m1.inverse();
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void reverseRows() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {3,2,1},
                {6,5,4},
                {9,8,7}});
        m1.reverseRows();
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void reverseColumns() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {7,8,9},
                {4,5,6},
                {1,2,3}});
        m1.reverseColumns();
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void rotateRight() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {7,4,1},
                {8,5,2},
                {9,6,3}});
        m1.rotateRight(1);
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void rotateLeft() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {3,6,9},
                {2,5,8},
                {1,4,7}});
        m1.rotateLeft(1);
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void add() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {0,0,0},
                {0,0,0},
                {0,0,0}});
        Matrix m3 = Matrix.add(m1,m2);
        Assertions.assertTrue(m3.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void resize() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {1,2,3,0},
                {4,5,6,0},
                {7,8,9,0},
                {0,0,0,0}});
        m1.resize(4,4);
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void sumRow() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Assertions.assertEquals(m1.sumRow(0), 6);
        Assertions.assertEquals(m1.sumRow(1), 15);
        Assertions.assertEquals(m1.sumRow(2), 24);
    }

    @org.junit.jupiter.api.Test
    void sumCol() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Assertions.assertEquals(m1.sumCol(0), 12);
        Assertions.assertEquals(m1.sumCol(1), 15);
        Assertions.assertEquals(m1.sumCol(2), 18);
    }

    @org.junit.jupiter.api.Test
    void leftShift() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {2,2,0},
                {5,5,0},
                {8,8,0}});
        m1.leftShift();
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void rightShift() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {0,1,2},
                {0,4,5},
                {0,7,8}});
        m1.rightShift();
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void upShift() {
        Matrix m1 = new Matrix(
                new int[][] {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9}});
        Matrix m2 = new Matrix(
                new int[][] {
                        {4,5,6},
                        {7,8,9},
                        {0,0,0}});
        m1.upShift();
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void downShift() {
        Matrix m1 = new Matrix(
            new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9}});
        Matrix m2 = new Matrix(
            new int[][] {
                {0,0,0},
                {1,2,3},
                {4,5,6}});
        m1.downShift();
        Assertions.assertTrue(m2.equals(m1));
    }

    @org.junit.jupiter.api.Test
    void getSetElement() {
        Matrix m1 = new Matrix(
                new int[][] {
                        {0,0,0},
                        {4,5,6},
                        {0,0,0}});
        m1.setElement(2,2, 2);
        Assertions.assertEquals(m1.getElement(0,0), 0);
        Assertions.assertEquals(m1.getElement(1,1), 5);
        Assertions.assertEquals(m1.getElement(2,2), 2);
    }


}