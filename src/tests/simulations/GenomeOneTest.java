package tests.simulations;

import environment.Environment;
import environment.IPopulationManager;
import environment.SimplePopulationManager;
import environment.StochasticSamplingManager;
import statistics.IReporter;
import genomes.AbstractGenome;
import genomes.GenomeOne;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class GenomeOneTest {
    Environment env;
    IPopulationManager manager;
    IReporter reporter = null;
    Set<AbstractGenome> genomes;

    int threads = 4;
    long seed = 7739;
    int populationSize = 100;
    int generations= 100;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void teardown() {
        if(env != null) {
            env.shutdown();
        }
    }

    @BeforeEach
    public void createGenomes() {
        Random rand = new Random(seed);
        genomes = new HashSet<>();
        for(int i = 0; i < populationSize; i++) {
            genomes.add(new GenomeOne(rand.nextLong()));
        }
    }

    @Test
    public void Stochastic() {
        //Local Variables
        int mutations = 50, crossovers = 50;

        //Environment
        reporter = null;
        manager = new StochasticSamplingManager(populationSize, mutations, crossovers);
        env = new Environment(threads, seed, manager, reporter);

        //Run
        env.stepGeneration(genomes, generations);
        AbstractGenome alpha = env.getAlpha(genomes);
        System.out.println(alpha.toString());
    }

    @Test
    public void Simple() {
        //Local Variables

        //Environment
        reporter = null;
        manager = new SimplePopulationManager(populationSize);
        env = new Environment(threads, seed, manager, reporter);

        //Run
        env.stepGeneration(genomes, generations);
        AbstractGenome alpha = env.getAlpha(genomes);
        System.out.println(alpha.toString());
    }
}
