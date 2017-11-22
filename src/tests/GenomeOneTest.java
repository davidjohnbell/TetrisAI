package tests;

import ai.AbstractGenome;
import ai.GenomeOne;
import game.Board;
import game.Shape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Matrix;


class GenomeOneTest {
    private AbstractGenome genome;
    private Matrix base;
    private Board board;
    private Shape shape;

    @BeforeEach
    void setUp() {
        genome = new GenomeOne(0.2f, 0.2f, 0);
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
        board = new Board(base);
        shape = new  Shape(
            new int[][] {
                {2,0},
                {2,2}});
    }

    @Test
    void evaluateBoardDoesNotModify() {
        Board copy = new Board(board);
        genome.evaluateBoard(copy);
        Assertions.assertTrue(board.equals(copy));
    }

    @Test
    void makeMoveDoesNotModify() {
        Board copy = new Board(new Matrix(base));
        genome.makeMove(board, shape);
        Assertions.assertTrue(copy.equals(board));
    }
}