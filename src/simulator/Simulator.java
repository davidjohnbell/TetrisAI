package simulator;

import ai.TetrisGenome;
import game.Game;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.*;

public class Simulator {

    private final float crossRate;
    private Random rand;
    private ThreadPoolExecutor executor;
    private Game[] population;
    public TetrisGenome alpha;

    public Simulator(int threads, int size, float crossRate, float mutationRate, float mutationStep, long seed, int width, int height) {
        rand = new Random(seed);
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        population = createPopulation(size, width, height, mutationStep, mutationRate, rand);
        this.crossRate = crossRate;
    }

    public static void main(String[] args) {
        int threads;
        int size;
        float crossRate;
        float mutationRate;
        float mutationStep;
        long seed;
        int width;
        int height;
        Simulator sim = null;
        try {
            threads = Integer.parseInt(args[0]);
            size = Integer.parseInt(args[1]);
            crossRate = Float.parseFloat(args[2]);
            mutationRate = Float.parseFloat(args[3]);
            mutationStep = Float.parseFloat(args[4]);
            seed = Long.parseLong(args[5]);
            width = Integer.parseInt(args[6]);
            height = Integer.parseInt(args[7]);
            sim = new Simulator(threads, size, crossRate, mutationRate, mutationStep, seed, width, height);
        }
        catch(Exception e) {
            System.out.println("Invalid command line args, try again:");
            System.out.println("int threads, int size, float mutationRate, float mutationStep, long seed, int width, int height");
            System.exit(-1);
        }

        if(sim != null) {
            sim.simulate(5);
            System.out.println(sim.alpha.fitness);
            sim.shutdown();
        }
    }

    public void simulate(int nGenerations) {
        for(int i = 0; i < nGenerations; i++) {
            stepGeneration();
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

    private void stepGeneration() {
        executeGeneration();
        Arrays.sort(population, (game1, game2) ->
            game1.genome.fitness < game2.genome.fitness ? -1 : game1.genome.fitness == game2.genome.fitness ? 0 : 1);

        TetrisGenome beta = population[population.length - 1].genome;
        if(alpha == null || alpha.fitness < beta.fitness) { //java is short circuit
            alpha = beta;
        }

        if(rand.nextFloat() < crossRate) {
            crossGenes();
        }

        mutateGenes();
    }

    public void executeGeneration() {
        Collection<Future<?>> futures = new LinkedList<>();
        for(int i = 0; i < population.length; i++) {
            population[i].reset();
            futures.add(executor.submit(population[i]));
        }
        for(Future<?> future : futures) {
            try {
                future.get();
            }
            catch (Exception e) {
                System.out.println("InterruptException occurred");
            }
        }
        futures.clear();
    }

    private void shutdown() {
        try {
            if (executor != null) {
                executor.shutdown();
            }
            while (!executor.isTerminated()) {
                System.out.println("Shutting down...");
                executor.awaitTermination(60, TimeUnit.SECONDS);
            }
        }
        catch (Exception e) {System.out.println("InterruptedException occurred.");}
    }

    private void mutateGenes() {
        for(Game game : population) {
            if(game.genome != alpha) {
                game.genome.maybeMutate();
            }

        }
    }

    private void crossGenes() {
        TetrisGenome dad = pickRandom(population).genome;
        TetrisGenome mom = pickRandom(population).genome;
        population[0].genome = dad.crossover(mom);
    }

    private <T> T pickRandom(T[] array) {
        return array[rand.nextInt(array.length)];
    }

}
