package ip91.chui.oleh.util.evolutionaryAlgorithm;

import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.BackpackConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.SalesmanConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Result;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.RuntimeInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SequenceEvolutionaryAlgorithm {

    private final BackpackConditionData backpackConditionData;
    private final SalesmanConditionData salesmanConditionData;

    private final Population population;

    public Result process() {
        EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithm(backpackConditionData, salesmanConditionData);
        evolutionaryAlgorithm.init();

        return evolutionaryAlgorithm.run(population, new RuntimeInfo(null, 0));
    }

}
