package simulator;

import ai.TetrisGenome;
import game.Game;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Simulator {

    private final float crossRate;
    private final int width;
    private final int height;
    private Random rand;
    private ThreadPoolExecutor executor;
    private Game[] population;


    public Simulator(int threads, int size, float crossRate, float mutationRate, float mutationStep, long seed, int width, int height) {
        rand = new Random(seed);
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        population = createPopulation(size, mutationStep, mutationRate);
        this.crossRate = crossRate;
        this.width = width;
        this.height = height;
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
        finally {

        }

    }

    private Game[] createPopulation(int size, int width, int height, float mutationStep, float mutationRate, Random seedGenerator) {
        long gameSeed = seedGenerator.nextLong();
        Game[] population = new Game[size];
        for(int i = 0; i < size; i++) {
            TetrisGenome genome = new TetrisGenome(mutationRate, mutationStep, seedGenerator.nextLong());
            population[i] = new Game(genome, width, height, gameSeed);
        }
        return  population;
    }

    private void begin() {

    }

    private void shutdown() {
        try {
            if (executor != null) {
                executor.shutdown();
            }
            while (!executor.isTerminated()) {
                executor.awaitTermination(60, TimeUnit.SECONDS);
            }
        }
        catch (Exception e) {System.out.println("InterruptedException occurred.");}
    }

    private void mutateGenes(Game[] population) {
        for(Game game : population) {
            game.genome.maybeMutate();
        }
    }

    private void crossGenes(Game[] population) {
        TetrisGenome dad = pickRandom(population).genome;
        TetrisGenome mom = pickRandom(population).genome;
        TetrisGenome baby = dad.crossover(mom);5

    }

    public <T> T pickRandom(T[] population) {
        return population[rand.nextInt(population.length)];
    }



}
