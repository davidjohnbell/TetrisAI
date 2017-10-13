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
        Matrix is = Board.applyShape(shape, board.board);
        Assertions.assertTrue(is.equals(shouldBe));
    }

    @Test
    void collision() {
        board.board.setElement(1,0, 5);
        Matrix applied = Board.applyShape(shape, board.board);
        boolean oops = Board.collision(applied, board.board);
        Assertions.assertTrue(oops);
    }

    @Test
    void rowsCleared() {
        for(int i = 0; i < board.board.getHeight(); i += 2) {
            for(int j = 0; j < board.board.getWidth(); j++) {
                board.board.setElement(i, j, 1);
            }
        }
        for(int i = 0; i < board.board.getHeight(); i ++) {
            board.board.setElement(i, 0, 1);
        }
        int[] cleared = Board.getFullRows(board.board);
        Assertions.assertArrayEquals(cleared,
                new int[] {1,0,1,0,1,0,1,0});
        Assertions.assertEquals(4, board.board.sumCol(0));
        Assertions.assertEquals(0, board.board.sumCol(1));
    }

    @Test
    void win() {
        board.board.setElement(0, 0, 1);
        boolean win = Board.isGameOver(board.board);
        //Unimplemented
        Assertions.assertTrue(win);
    }

}