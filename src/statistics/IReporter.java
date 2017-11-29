package statistics;

import genomes.AbstractGenome;
import java.util.Set;

public interface IReporter {
    void report(Set<AbstractGenome> genomes);
}
