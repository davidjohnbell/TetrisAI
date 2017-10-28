package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import game.Shape;
import utils.Matrix;
import game.Board;

class BoardTest {
    private Board board = new Board(
        new Matrix(
            new int[][] {
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}}),
        1234);
    private Shape shape = new Shape(
        new int[][] {
            {1,1,0},
            {0,1,1}});

    @Test
    void applyShape() {
        shape.x += 1;
        shape.y += 1;
        shape.rotate(1);
        Matrix shouldBe = new Matrix(
            new int[][] {
                {0,0,0,0},
                {0,0,1,0},
                {0,1,1,0},
                {0,1,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}});
        Matrix is = board.applyShape(shape);
        Assertions.assertTrue(is.equals(shouldBe));
    }

    @Test
    void dropShape() {
        int testIndex = board.getHeight() - 3;
        board.setElement(testIndex, 1, 5);
        Assertions.assertTrue(board.sumRow(testIndex) == 5);
        Assertions.assertTrue(board.sumRow(testIndex - 1) == 2);
        Assertions.assertTrue(board.sumRow(testIndex - 2) == 2);
    }

    @Test
    void dropThreeShapes() {
        for(int i = 0; i < 3; i++) {
            board.dropShape(shape);
        }
        for(int i = 0; i < board.getHeight(); i++) {
            Assertions.assertTrue(board.sumRow(1) <= 2);
        }
    }

    @Test
    void collision() {
        shape.rotate(1);
        board.setElement(1,0, 5);
        Matrix applied = board.applyShape(shape);
        boolean oops = board.collision(applied);
        Assertions.assertTrue(oops);
    }

    @Test
    void getFullRows() {
        for(int i = 0; i < board.getWidth(); i ++) {
            board.setElement(0, i, 1);
            board.setElement(2, i, 1);
        }
        board.setElement(3, 0, 10);
        int[] cleared = board.getFullRows();
        Assertions.assertArrayEquals(cleared, new int[] {1,0,1,0,0,0,0,0});
    }

    @Test
    void collapseRows() {
        for(int i = 0; i < board.getHeight(); i += 2) {
            for(int j = 0; j < board.getWidth(); j++) {
                board.setElement(i, j, 1);
            }
        }
        for(int i = 0; i < board.getHeight(); i ++) {
            board.setElement(i, 0, 1);
        }
        int[] cleared = board.getFullRows();
        board.collapseRows(cleared);
        int sum = board.sumCol(0);
        Assertions.assertEquals(4,sum);
    }

    @Test
    void isGameOver() {
        board.setElement(0, 0, 1);
        Assertions.assertTrue(board.isGameOver());
    }

}