package environment;

import genomes.AbstractGenome;
import java.util.*;

public class StochasticSamplingManager implements IPopulationManager {
    private int numPopulation;
    private int numMutation;
    private int numCrossOver;
    private Random rand;

    public StochasticSamplingManager(int numPopulationPointers, int numMutationPointers, int numCrossOverPointers) {
        numPopulation = numPopulationPointers;
        numMutation = numMutationPointers;
        numCrossOver = numCrossOverPointers;
        rand = new Random();
    }

    /**
     * Mutates the number of individuals specified in the constructor
     * using Stochastic Universal Sampling.
     * @param genomes the population to mutate
     */
    @Override
    public void mutate(Set<AbstractGenome> genomes) {
        ArrayList<AbstractGenome> chosen = SUS(genomes, numMutation);
        for(AbstractGenome genome : chosen) {
            genome.mutate();
        }
    }

    /**
     * Breeds the number of individuals specified in the constructor
     * using Stochastic Universal Sampling. This method might not make
     * sense if the genome's crossOver method can't handle equal genomes
     * @param genomes the population to breed
     */
    @Override
    public void crossOver(Set<AbstractGenome> genomes) {
        ArrayList<AbstractGenome> chosen = SUS(genomes, numCrossOver);
        for(int i = 0, l = chosen.size() - 1; i < l; i++) {
            genomes.add(chosen.get(i).crossover(chosen.get(i+1)));
        }
    }

    /**
     * Selects the number of individuals specified in the constructor
     * using Stochastic Universal Sampling. The selected individuals
     * become the new population.
     * @param genomes the population to cull
     */
    @Override
    public void select(Set<AbstractGenome> genomes) {
        ArrayList<AbstractGenome> chosen = SUS(genomes, numPopulation);
        genomes.clear();
        genomes.addAll(chosen);
    }

    /**
     * Stochastic Universal Sampling. It works like a roulette wheel
     * with multiple pointers. Each genome has a proportionate amount
     * of slots based off of it's fitness.
     * @param genomes the population to select from
     * @param numPointers the number of individuals to select
     * @return
     */
    private ArrayList<AbstractGenome> SUS(Set<AbstractGenome> genomes, int numPointers) {
        int totalFitness = 0;
        for (AbstractGenome genome : genomes) {
            totalFitness += genome.fitness;
        }
        float distanceBetweenPointers = totalFitness / numPointers;
        float start =  nextFloat(0, distanceBetweenPointers);
        float[] pointers = new float[numPointers];
        for(int i = 0; i < numPointers; i++) {
            pointers[i] = start + i * distanceBetweenPointers;
        }
        return RWS(genomes, pointers);
    }

    private ArrayList<AbstractGenome> RWS(Set<AbstractGenome> genomes, float[] pointers) {
        AbstractGenome[] sorted = toDescendingArray(genomes);
        ArrayList<AbstractGenome> chosen = new ArrayList<>();
        for(float pointer : pointers) {
            int i = 0;
            int sum = 0;
            while(sum < pointer) {
                sum += sorted[i].fitness;
                i++;
            }
            i = i < sorted.length ? i : sorted.length - 1;
            chosen.add(sorted[i].makeCopy());
        }
        return chosen;
    }

    private AbstractGenome[] toDescendingArray(Set<AbstractGenome> genomes) {
        AbstractGenome[] array = new AbstractGenome[genomes.size()];
        array = genomes.toArray(array);
        Arrays.sort(array, Collections.reverseOrder(Comparator.comparingInt(genome -> genome.fitness)));
        return array;
    }

    private float nextFloat(float lowerBound, float upperBound) {
        return lowerBound + (upperBound - lowerBound) * rand.nextFloat();
    }
}
