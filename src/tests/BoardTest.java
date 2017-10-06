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
        //Unimplemented
        Assertions.assertTrue(false);
    }

    @Test
    void rowsCleared() {
        //Unimplemented
        Assertions.assertTrue(false);
    }

    @Test
    void win() {
        //Unimplemented
        Assertions.assertTrue(false);
    }

}