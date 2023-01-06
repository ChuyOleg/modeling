package ip91.chui.oleh.util.evolutionaryAlgorithm.generationReplacement;

import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;

import java.util.ArrayList;
import java.util.List;

public class AllOffspringIntoPopulationGenerationReplacement implements GenerationReplacement {

    @Override
    public void process(Population population, List<Individual> offspring) {
        List<Individual> haveToBeDeleted = new ArrayList<>();
        List<Individual> haveToBeAdded = new ArrayList<>();

        int offSpringIndex = 0;

        outerLoop:
        for (Individual individual : population.getIndividuals()) {
            while (true) {
                if (offSpringIndex >= offspring.size()) {
                    break outerLoop;
                }
                Individual child = offspring.get(offSpringIndex);
                if (isDead(child)) {
                    offSpringIndex++;
                    continue;
                }

                haveToBeDeleted.add(individual);
                haveToBeAdded.add(child);
                offSpringIndex++;
                break;
            }
        }

        haveToBeDeleted.forEach(population.getIndividuals()::remove);
        haveToBeAdded.forEach(population.getIndividuals()::add);
    }

    private boolean isDead(Individual individual) {
        return individual.getFitness() == Integer.MIN_VALUE;
    }

}
