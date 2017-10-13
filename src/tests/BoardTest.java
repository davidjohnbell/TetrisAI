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
            {0,1,1},
            {0,0,0}});

    @Test
    void applyShape() {
        shape.x += 1;
        shape.y += 1;
        shape.rotate(1);
        Matrix shouldBe = new Matrix(
            new int[][] {
                {0,0,0,0},
                {0,0,0,1},
                {0,0,1,1},
                {0,0,1,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}});
        Matrix is = board.applyShape(shape);
        Assertions.assertTrue(is.equals(shouldBe));
    }

    @Test
    void collision() {
        board.setElement(1,0, 5);
        Matrix applied = board.applyShape(shape);
        boolean oops = board.collision(applied);
        Assertions.assertTrue(oops);
    }

    @Test
    void getFullRows() {
        for(int i = 0; i < board.getHeight(); i += 2) {
            for(int j = 0; j < board.getWidth(); j++) {
                board.setElement(i, j, 1);
            }
        }
        int[] cleared = board.getFullRows();
        Assertions.assertArrayEquals(cleared, new int[] {1,0,1,0,1,0,1,0});
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
        Board collapsed = board.collapseRows(cleared);
        int sum = collapsed.sumCol(0);
        Assertions.assertEquals(4,sum);
    }

    @Test
    void isGameOver() {
        board.setElement(0, 0, 1);
        boolean lost = board.isGameOver();
        Assertions.assertTrue(lost);
    }

}