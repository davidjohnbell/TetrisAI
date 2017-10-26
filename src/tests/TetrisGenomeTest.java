package tests;

import ai.TetrisGenome;
import game.Board;
import game.Shape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Matrix;


class TetrisGenomeTest {
    TetrisGenome genome;
    Matrix base;
    Board board;

    @BeforeEach
    void setUp() {
        genome = new TetrisGenome(0.2f, 0.2f, 0);
        base = new Matrix(
            new int[][] {
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,1},
                {0,0,2,2},
                {3,3,3,3}});
        board = new Board(base, 1234);
    }

    @Test
    void evaluateBoard() {
        Board copy = new Board(Matrix.copy(base), 1235);
        genome.evaluateBoard(board);
        Assertions.assertTrue(board.equals(copy));
    }

}