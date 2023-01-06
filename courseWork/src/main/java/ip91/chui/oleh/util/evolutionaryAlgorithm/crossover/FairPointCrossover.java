package ip91.chui.oleh.util.evolutionaryAlgorithm.crossover;

import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.chromosomeController.ChromosomeController;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;


public class FairPointCrossover extends AbstractPointCrossover {

    public FairPointCrossover(ChromosomeController chromosomeController) {
        super(chromosomeController);
    }

    @Override
    protected int getPoint(Individual individual) {
        return individual.getChromosome().length / 2;
    }

}
