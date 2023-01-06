package ip91.chui.oleh.util.evolutionaryAlgorithm.selection;

import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;

import java.util.Arrays;
import java.util.List;

public class TwoBestSelection implements Selection {

    @Override
    public List<Individual> process(Population population) {
        Individual theBestIndividual = population.getIndividuals().get(population.getIndividuals().size() - 1);
        Individual secondBestIndividual = population.getIndividuals().get(population.getIndividuals().size() - 2);

        return Arrays.asList(secondBestIndividual, theBestIndividual);
    }

}
