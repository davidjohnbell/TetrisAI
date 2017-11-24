package environment;

import genomes.AbstractGenome;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class SimplePopulationManager implements IPopulationManager {
    private int keep;
    private int mutate;
    private int cross;

    public SimplePopulationManager(int poolSize) {
        keep = (int)Math.floor((float)poolSize / 2);
        mutate = (int)Math.floor((float)poolSize / 4);
        cross = (int)Math.ceil((float)poolSize / 4);
    }

    @Override
    public void mutate(Set<AbstractGenome> genomes) {
        AbstractGenome[] sorted = toDescendingArray(genomes);
        for(int i = 0; i < mutate; i++) {
            AbstractGenome mutation = sorted[i].makeCopy();
            mutation.mutate();
            genomes.add(mutation);
        }
    }

    @Override
    public void crossOver(Set<AbstractGenome> genomes) {
        AbstractGenome[] sorted = toDescendingArray(genomes);
        for(int i = 0; i < cross; i++) {
            AbstractGenome mom = sorted[i];
            AbstractGenome dad = sorted[i+1];
            genomes.add(mom.crossover(dad));
        }
    }

    @Override
    public void select(Set<AbstractGenome> genomes) {
        AbstractGenome[] sorted = toDescendingArray(genomes);
        genomes.clear();
        for(int i = 0; i < keep; i++) {
            genomes.add(sorted[i]);
        }
    }

    private AbstractGenome[] toDescendingArray(Set<AbstractGenome> genomes) {
        AbstractGenome[] array = new AbstractGenome[genomes.size()];
        array = genomes.toArray(array);
        Arrays.sort(array, Collections.reverseOrder(Comparator.comparingInt(genome -> genome.fitness)));
        return array;
    }
}
