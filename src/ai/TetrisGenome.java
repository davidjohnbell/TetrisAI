package ai;

import game.Board;
import java.util.Random;

public class TetrisGenome implements ITetrisGenome<TetrisGenome> {
    private float mutateRate;
    private float mutateStep;
    private float[] genes;
    private Random random;

    public TetrisGenome(float mutateRate, float mutateStep, long seed) {
        this.mutateRate = mutateRate;
        this.mutateStep = mutateStep;
        this.random = new Random(seed);
    }

    public TetrisGenome() {
        this.mutateRate = 0.1f;
        this.mutateStep = 0.2f;
        this.random = new Random();
    }

    public void mutate() {

    }

    public TetrisGenome crossover(TetrisGenome partner) {
        return null;
    }


    public void evaluateBoard(Board board) {

    }
}
