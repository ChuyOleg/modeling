package ip91.chui.oleh.util.evolutionaryAlgorithm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
public class Population {

    private final List<Individual> individuals;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Individual individual : individuals) {
            builder.append(individual);
            builder.append("\n");
        }
        return builder.toString();
    }
}
