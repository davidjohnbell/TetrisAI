package simulator;

import ai.TetrisGenome;

import java.util.Random;

public class Simulator {

    private final int threads;
    private final int size;
    private final float crossRate;
    private final float mutationRate;
    private final float mutationStep;
    private final long seed;
    private final int width;
    private final int height;
    private Random rand;

    private TetrisGenome[] population;

    public Simulator(int threads, int size, float crossRate, float mutationRate, float mutationStep, long seed, int width, int height) {
        this.threads = threads;
        this.size = size;
        this.crossRate = crossRate;
        this.mutationRate = mutationRate;
        this.mutationStep = mutationStep;
        this.seed = seed;
        this.width = width;
        this.height = height;
        this.rand = new Random(seed);

    }

    public static void main(String[] args) {
        try {
            int threads = Integer.parseInt(args[0]);
            int size = Integer.parseInt(args[1]);
            float crossRate = Float.parseFloat(args[2]);
            float mutationRate = Float.parseFloat(args[3]);
            float mutationStep = Float.parseFloat(args[4]);
            long seed = Long.parseLong(args[5]);
            int width = Integer.parseInt(args[6]);
            int height = Integer.parseInt(args[7]);

            Simulator sim = new Simulator(threads, size, crossRate, mutationRate, mutationStep, seed, width, height);
            sim.begin();
        }
        catch(Exception e) {
            System.out.println("Invalid command line args, try again:");
            System.out.println("int threads, int size, float mutationRate, float mutationStep, long seed, int width, int height");
        }

    }

    private TetrisGenome[] createPopulation(int size, float mutationStep, float mutationRate) {
        TetrisGenome[] population = new TetrisGenome[size];
        for(int i = 0; i < size; i++) {
            population[i] = new TetrisGenome(mutationRate, mutationStep, rand.nextLong());
        }
        return  population;
    }

    private void begin() {

    }

    private void mutateGenes(TetrisGenome[] population) {
        for(TetrisGenome genome : population) {
            genome.maybeMutate();
        }
    }

    private void crossGenes(TetrisGenome[] population) {
        TetrisGenome mom = pickRandom(population);
        TetrisGenome mom = pickRandom(population);
    }

    TetrisGenome pickRandom(TetrisGenome[] population) {
        return population[rand.nextInt(population.length)];
    }



}
