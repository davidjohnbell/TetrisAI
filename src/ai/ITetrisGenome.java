package ai;

import game.Board;

public interface ITetrisGenome<T> {
    void mutate();
    T crossover(T partner);
    int evaluateBoard(Board board);
}
