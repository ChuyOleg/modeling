package ip91.chui.oleh.util.evolutionaryAlgorithm.selection;

import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;

import java.util.List;
import java.util.stream.Collectors;

public class HalfPopulationSelection implements Selection {

    @Override
    public List<Individual> process(Population population) {
        List<Individual> sample = population.getIndividuals().stream()
                .skip(population.getIndividuals().size() / 2)
                .collect(Collectors.toList());

        if (sample.size() % 2 == 1) {
            sample.remove(0);
        }

        return sample;
    }

}
