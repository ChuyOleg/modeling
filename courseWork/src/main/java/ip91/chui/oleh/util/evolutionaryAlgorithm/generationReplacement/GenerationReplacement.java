package ip91.chui.oleh.util.evolutionaryAlgorithm.generationReplacement;

import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;

import java.util.List;

public interface GenerationReplacement {

    void process(Population population, List<Individual> offspring);

}
