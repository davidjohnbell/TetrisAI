package main;

import environment.Environment;
import environment.IPopulationManager;
import environment.SimplePopulationManager;
import environment.StochasticSamplingManager;
import genomes.AbstractGenome;
import genomes.GenomeOne;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneticAlgorithmTetris {

    public static void main(String[] args) {
        IPopulationManager manager = new SimplePopulationManager(100);
        Environment env = new Environment(4, 7739, manager);
        Set<AbstractGenome> genomes = createInitialPopulation(200, 7739);
        env.stepGeneration(genomes, 100);
        AbstractGenome alpha = env.getAlpha(genomes);
        System.out.println(alpha.toString());
        System.out.println(alpha.fitness);
        env.shutdown();
    }

    private static Set<AbstractGenome> createInitialPopulation(int size, long seed) {
        Random rand = new Random(seed);
        HashSet<AbstractGenome> genomes = new HashSet<>();
        for(int i = 0; i < size; i++) {
            genomes.add(new GenomeOne(rand.nextLong()));
        }
        return genomes;
    }
}
