package ip91.chui.oleh.util.evolutionaryAlgorithm.crossover;

import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.chromosomeController.ChromosomeController;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;

import java.util.Random;

public class RandomPointCrossover extends AbstractPointCrossover {

    private final Random random;

    public RandomPointCrossover(ChromosomeController chromosomeController, Random random) {
        super(chromosomeController);
        this.random = random;
    }

    @Override
    protected int getPoint(Individual individual) {
        return random.nextInt(individual.getChromosome().length - 1) + 1;
    }
}
