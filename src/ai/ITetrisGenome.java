package ai;

import game.Board;
import game.Shape;

public interface ITetrisGenome<T> {
    int fitness = 0;
    void maybeMutate();
    T crossover(T partner);
    int evaluateBoard(Board board);
    Board makeMove(Board board, Shape shape);
}
