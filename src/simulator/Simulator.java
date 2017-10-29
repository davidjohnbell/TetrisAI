package simulator;

import ai.TetrisGenome;
import game.Game;

import java.util.*;
import java.util.concurrent.*;

public class Simulator {

    private final float crossRate;
    private Random rand;
    private ThreadPoolExecutor executor;
    private Game[] population;
    private TetrisGenome alpha;

    private Simulator(int threads, int size, float crossRate, float mutationRate, float mutationStep, long seed, int width, int height) {
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
            sim.simulate(1000);
            sim.shutdown();
        }
    }

    private void simulate(int nGenerations) {
        for(int i = 0; i < nGenerations; i++) {
            stepGeneration();
            System.out.println(alpha.fitness);
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
        Arrays.sort(population, Comparator.comparingInt(game -> game.genome.fitness));

        TetrisGenome beta = population[population.length - 1].genome;
        if(alpha == null || alpha.fitness < beta.fitness) { //java is short circuit
            alpha = beta;
        }

        if(rand.nextFloat() < crossRate) {
            crossGenes();
        }
        mutateGenes();
    }

    private void executeGeneration() {
        Collection<Future<?>> futures = new LinkedList<>();

        for(Game game : population) {
            game.reset();
            futures.add(executor.submit(game));
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
        Arrays.sort(population, Comparator.comparingInt(game -> game.genome.fitness));
        TetrisGenome mom = pickRandom(population).genome;
        int size = population.length - 1;
        for(int i = 0; i < size; i++) {
            if(population[i].genome.fitness >= 0) {
                population[0].genome = alpha.crossover(mom);
                break;
            }
        }

    }

    private <T> T pickRandom(T[] array) {
        return array[rand.nextInt(array.length)];
    }

}
