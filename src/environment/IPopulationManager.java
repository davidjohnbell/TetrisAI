package environment;

import genomes.AbstractGenome;

import java.util.Set;

public interface IPopulationManager {

    /**
     * Decides which individuals to mutate.
     * How the individual is mutated is up to
     * the specific genome implementation.
     * @param genomes the population to mutate
     */
    void mutate(Set<AbstractGenome> genomes);

    /**
     * Decides which individuals to breed.
     * How the individuals are crossed is up to
     * the specific genome implementation.
     * @param genomes the population to breed
     */
    void crossOver(Set<AbstractGenome> genomes);

    /**
     * The environment uses this method to
     * control the size of the population.
     * @param genomes the population
     */
    void select(Set<AbstractGenome> genomes);
}
