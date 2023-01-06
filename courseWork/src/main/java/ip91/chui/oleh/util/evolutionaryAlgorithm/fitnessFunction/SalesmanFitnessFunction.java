package ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction;

import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.SalesmanConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SalesmanFitnessFunction implements FitnessFunction {

    private final SalesmanConditionData conditionData;

    @Override
    public int calculate(Individual individual) {
        int fitness = 0;
        int firstCity = 0;
        int lastCity = (int) individual.getChromosome()[individual.getChromosome().length - 1];
        int previousCity = firstCity;

        for (int gene = 0; gene < individual.getChromosome().length; gene++) {
            int currentCity = (int) individual.getChromosome()[gene];

            fitness += conditionData.getRoadMatrix()[previousCity][currentCity];

            previousCity = currentCity;
        }

        fitness += conditionData.getRoadMatrix()[lastCity][firstCity];

        return fitness;
    }

}
