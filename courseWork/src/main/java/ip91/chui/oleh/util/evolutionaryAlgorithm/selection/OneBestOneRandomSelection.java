package ip91.chui.oleh.util.evolutionaryAlgorithm.selection;

import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class OneBestOneRandomSelection implements Selection {

    private final Random random;

    @Override
    public List<Individual> process(Population population) {
        int randomNum = random.nextInt(population.getIndividuals().size() - 1);

        Individual theBestIndividual = population.getIndividuals().get(population.getIndividuals().size() - 1);
        Individual randomIndividual = population.getIndividuals().get(randomNum);

        return Arrays.asList(randomIndividual, theBestIndividual);
    }

}
