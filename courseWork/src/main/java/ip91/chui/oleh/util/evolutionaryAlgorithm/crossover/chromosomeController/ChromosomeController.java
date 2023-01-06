package ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.chromosomeController;

import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;

public interface ChromosomeController {

    Object[] createUsingPointCrossover(Individual parent1, Individual parent2, int point);

}
