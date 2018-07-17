package environment;

import genomes.AbstractGenome;
import javafx.util.Pair;
import java.util.*;

public class StochasticSamplingManager implements IPopulationManager {
    private int keep;
    private int mutate;
    private int cross;
    private Random rand;

    public StochasticSamplingManager(int poolSize) {
        keep = (int)Math.floor((float)poolSize / 2);
        mutate = (int)Math.floor((float)poolSize / 4);
        cross = (int)Math.ceil((float)poolSize / 4);
        rand = new Random();
    }

    /**
     * Mutates a number of individuals
     * using Stochastic Universal Sampling.
     * @param genomes the population to mutate
     */
    @Override
    public void mutate(Set<AbstractGenome> genomes) {
        ArrayList<AbstractGenome> chosen = SUS(genomes, this.mutate);
        for(AbstractGenome genome : chosen) {
            AbstractGenome mutated = genome.makeCopy();
            mutated.mutate();
            genomes.add(mutated);
        }
    }

    /**
     * Breeds a number of individuals using Stochastic Universal Sampling.
     * @param genomes the population to breed
     */
    @Override
    public void crossOver(Set<AbstractGenome> genomes) {
        ArrayList<AbstractGenome> chosen;
        Pair[] wheel = makeWheel(genomes);
        for(int i = 0; i < this.cross; i++) {
            chosen = selectFromWheel(wheel, 2);
            genomes.add(chosen.get(0).crossover(chosen.get(1)));
        }
    }

    /**
     * Selects a number of individuals using Stochastic Universal Sampling.
     * The selected individuals become part of the new population.
     * @param genomes the population to cull
     */
    @Override
    public void select(Set<AbstractGenome> genomes) {
        AbstractGenome alpha = genomes
                .stream()
                .reduce(genomes.iterator().next(), (cur, acc) -> cur.fitness > acc.fitness ? cur : acc);
        ArrayList<AbstractGenome> chosen = SUS(genomes, this.keep - 1);
        genomes.clear();
        genomes.add(alpha);
        genomes.addAll(chosen);
    }

    /**
     * Stochastic Universal Sampling works like a roulette wheel
     * with multiple pointers. Each genome has a proportionate
     * chance of being selected as its fitness.
     * @param genomes the population to select from
     * @param numPointers the number of individuals to select
     * @return an ArrayList of the selected individuals
     */
    private ArrayList<AbstractGenome> SUS(Set<AbstractGenome> genomes, int numPointers) {
        Pair[] wheel = makeWheel(genomes);
        return selectFromWheel(wheel, numPointers);
    }

    /**
     * Constructs an list of pairs of size 3.
     * The first index contains the individuals' normalized start positions,
     * the second index contains the individuals' normalized end positions,
     * the last index contains the individuals.
     * @param genomes the genomes that will make up the wheel
     * @return [float[], float[], AbstractGenomes[]]
     */
    private Pair[] makeWheel(Set<AbstractGenome> genomes) {
        float[] starts = new float[genomes.size()];
        float[] ends = new float[genomes.size()];
        AbstractGenome[] answers = new AbstractGenome[genomes.size()];
        genomes.toArray(answers);

        int total = genomes.stream().mapToInt(g -> g.fitness).sum();
        float top = 0;
        for(int i = 0; i < answers.length; i++){
            float f = answers[i].fitness / total;
            starts[i] = top;
            ends[i] = top + f;
            top += f;
        }
        return new Pair[]{new Pair<>("starts", starts), new Pair("ends", ends), new Pair("answers", answers)};
    }

    /**
     * Selects individuals from the wheel by moving a pointer across
     * the roulette wheel. Each individual has a normalized probability
     * of being selected from their fitness. The pointer wraps around the wheel.
     * @param wheel the roulette wheel of normalized lengths to "catch" the pointer
     * @param n the number of individuals to select
     * @return the selected individuals
     */
    private ArrayList<AbstractGenome> selectFromWheel(Pair[] wheel, int n) {
        float stepSize = 1.0f/n;
        ArrayList<AbstractGenome> answers = new ArrayList<>();
        float r = nextFloat(0, 1);
        answers.add(search(wheel, r));
        while(answers.size() < n) {
            r += stepSize;
            if(r > 1) r %= 1;
            answers.add(search(wheel, r));
        }
        return answers;
    }

    /**
     * Scans across the wheel, when the pointer falls between a start and end value
     * the corresponding individual has been selected.
     * @param wheel the Stochastic Universal Sampling wheel
     * @param num the pointer position on the wheel
     * @return the corresponding individual
     */
    private AbstractGenome search(Pair[] wheel, float num) {
        float[] starts = (float[]) wheel[0].getValue();
        float[] ends = (float[]) wheel[1].getValue();
        AbstractGenome[] answers = (AbstractGenome[]) wheel[2].getValue();

        AbstractGenome selected = answers[0];
        for(int i = 0; i < starts.length; i++) {
            if(starts[i] <= num && num <= ends[i]) {
                selected = answers[i];
                break;
            }
        }
        return selected;
    }

    private float nextFloat(float lowerBound, float upperBound) {
        return lowerBound + (upperBound - lowerBound) * rand.nextFloat();
    }
}
