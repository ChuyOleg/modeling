package ip91.chui.oleh.util.evolutionaryAlgorithm.crossover;

import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;

import java.util.List;

public interface Crossover {

    List<Individual> process(List<Individual> individuals);

}
