package environment;

import genomes.AbstractGenome;
import game.Game;
import statistics.IReporter;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Environment {
    private Random rand;
    private ThreadPoolExecutor executor;
    private IPopulationManager manager;
    private IReporter reporter;

    /**
     * Creates a new environment to test the genetic algorithms in.
     * @param threads the number of threads to run games in
     * @param seed the seed is passed down through the objects for deterministic results
     * @param manager the manager determines which genomes get to live on and breed
     * @param reporter NULLABLE, collects statistics about the population during every generation step
     */
    public Environment(int threads, long seed, IPopulationManager manager, IReporter reporter) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        rand = new Random(seed);
        this.manager = manager;
        this.reporter = reporter;
    }

    private void simulate(Set<AbstractGenome> genomes) {
        List<Game> games = makeGames(genomes);
        games.stream()
            .map(game -> executor.submit(game))
            .forEach(f -> {
                try {
                    f.get();
                }
                catch(Exception e){}
            });
    }

    private List<Game> makeGames(Set<AbstractGenome> genomes) {
        long seed = rand.nextLong();
        return genomes.stream()
            .map(genome -> new Game(genome, seed))
            .collect(Collectors.toList());
    }

    /**
     * Steps the generation n number of times. First, each genome
     * will be simulated. Second, the next generation is selected
     * using the population manager. Third, the selected generation
     * is mutated using the manager. Finally, the selected generation
     * is breed.
     * @param genomes the initial population
     * @param n the number of times to step
     */
    public void stepGeneration(Set<AbstractGenome> genomes, int n) {
        for(int i = 0; i < n; i++) {
            simulate(genomes);
            if(reporter != null) {
                reporter.report(genomes);
            }
            System.out.println(getAlpha(genomes).toString());
            manager.select(genomes);
            manager.mutate(genomes);
            manager.crossOver(genomes);
        }
    }

    public void shutdown() {
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

    public AbstractGenome getAlpha(Set<AbstractGenome> genomes) {
        return genomes.stream()
            .max(Comparator.comparingInt(genome -> genome.fitness))
            .get();
    }
}
