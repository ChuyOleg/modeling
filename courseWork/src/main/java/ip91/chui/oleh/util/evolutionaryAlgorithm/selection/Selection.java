package ip91.chui.oleh.util.evolutionaryAlgorithm.selection;

import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;

import java.util.List;

public interface Selection {

    List<Individual> process(Population population);

}
