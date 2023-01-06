package ip91.chui.oleh.util.evolutionaryAlgorithm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RuntimeInfo {

    private Individual bestIndividual;
    private int bestIndividualNotChangeCounter;

}
