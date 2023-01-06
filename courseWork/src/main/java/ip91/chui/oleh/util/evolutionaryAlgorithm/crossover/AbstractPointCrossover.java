package ip91.chui.oleh.util.evolutionaryAlgorithm.crossover;

import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.chromosomeController.ChromosomeController;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractPointCrossover implements Crossover {

    private final ChromosomeController chromosomeController;

    @Override
    public List<Individual> process(List<Individual> individuals) {
        List<Individual> offspring = new ArrayList<>();

        for (int indNum = 0; indNum < individuals.size(); indNum += 2) {
            Individual parent_1 = individuals.get(indNum);
            Individual parent_2 = individuals.get(indNum + 1);

            final int point = getPoint(parent_1);

            Object[] child_1Chromosome = chromosomeController.createUsingPointCrossover(parent_1, parent_2, point);
            Object[] child_2Chromosome = chromosomeController.createUsingPointCrossover(parent_2, parent_1, point);

            Individual individual_1 = new Individual(child_1Chromosome);
            Individual individual_2 = new Individual(child_2Chromosome);

            offspring.add(individual_1);
            offspring.add(individual_2);
        }

        return offspring;
    }

    protected abstract int getPoint(Individual individual);

}
