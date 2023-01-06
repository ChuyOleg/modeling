package ip91.chui.oleh.util.evolutionaryAlgorithm.populationGenerator;

import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.BackpackConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Getter
public class BackpackPopulationGenerator implements PopulationGenerator {

    private final BackpackConditionData conditionData;
    private final Random random;

    @Override
    public Population generate() {
        List<Individual> individuals = new ArrayList<>(Config.POPULATION_SIZE);

        for (int individualNum = 0; individualNum < Config.POPULATION_SIZE; individualNum++) {
            Object[] chromosome = new Object[conditionData.getWeightTable().length];
            int backpackCurrentWeight = 0;
            int price = 0;

            for (int gene = 0; gene < conditionData.getWeightTable().length; gene++) {
                boolean chance = random.nextInt(2) == 0;
                int backpackNewWeight = backpackCurrentWeight + conditionData.getWeightTable()[gene];

                if (chance && backpackNewWeight <= conditionData.getMaxWeight()) {
                    chromosome[gene] = true;
                    backpackCurrentWeight = backpackNewWeight;
                    price += conditionData.getPriceTable()[gene];
                } else {
                    chromosome[gene] = false;
                }
            }
            Individual individual = new Individual(chromosome);
            individual.setFitness(price);
            individuals.add(individual);
        }


        return new Population(individuals);
    }

}
