package simulator;

import game.Game;
import ai.TetrisGenome;

public class Simulator {

    private final int threads;
    private final int size;
    private final float mutationRate;
    private final float mutationStep;
    private final long seed;
    private final int width;
    private final int height;

    public Simulator(int threads, int size, float mutationRate, float mutationStep, long seed, int width, int height) {
        this.threads = threads;
        this.size = size;
        this.mutationRate = mutationRate;
        this.mutationStep = mutationStep;
        this.seed = seed;
        this.width = width;
        this.height = height;
    }

    public static void main(String[] args) {
        try {
            int threads = Integer.parseInt(args[0]);
            int size = Integer.parseInt(args[1]);
            float mutationRate = Float.parseFloat(args[2]);
            float mutationStep = Float.parseFloat(args[3]);
            long seed = Long.parseLong(args[4]);
            int width = Integer.parseInt(args[5]);
            int height = Integer.parseInt(args[6]);

            Simulator sim = new Simulator(threads, size, mutationRate, mutationStep, seed, width, height);
            sim.begin();
        }
        catch(Exception e) {
            System.out.println("Invalid command line args, try again:");
            System.out.println("int threads, int size, float mutationRate, float mutationStep, long seed, int width, int height");
        }

    }

    private void begin() {

    }


}
