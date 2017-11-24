package environment;

import genomes.AbstractGenome;
import game.Game;

import java.util.*;
import java.util.concurrent.*;

public class Environment {
    private Random rand;
    private ThreadPoolExecutor executor;
    private IPopulationManager manager;

    public Environment(int threads, long seed, IPopulationManager manager) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        rand = new Random(seed);
        this.manager = manager;
    }

    private void simulate(Set<AbstractGenome> genomes) {
        Future[] futures = new Future[genomes.size()];
        Game[] games = makeGames(genomes);
        int i = 0;
        for(Game game : games) {
            futures[i] = executor.submit(game);
            i++;
        }
        for(Future future : futures) {
            try {
                future.get();
            }
            catch (Exception e) {
                System.out.println("InterruptException occurred");
            }
        }
    }

    private Game[] makeGames(Set<AbstractGenome> genomes) {
        Game[] games = new Game[genomes.size()];
        long seed = rand.nextLong();
        int i = 0;
        for(AbstractGenome genome : genomes) {
            games[i] = new Game(genome, seed);
            i++;
        }
        return games;
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
        AbstractGenome alpha = null;
        for(AbstractGenome genome : genomes) {
            if(alpha == null || alpha.fitness < genome.fitness) {
                alpha = genome;
            }
        }
        return alpha;
    }
}
