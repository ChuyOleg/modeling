package ip91.chui.oleh.util.evolutionaryAlgorithm.mutation;

import ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config;
import ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class OppositeBooleanValueMutation implements Mutation {

    private final FitnessFunction fitnessFunction;
    private final Random random;

    @Override
    public void process(List<Individual> individuals) {
        for (Individual individual : individuals) {
            for (int geneNum = 0; geneNum < individual.getChromosome().length; geneNum++) {
                boolean chance = random.nextInt(Config.MUTATION_MEASURE) < Config.MUTATION_PERCENTAGE;
                if (chance) {
                    boolean geneValue = (boolean) individual.getChromosome()[geneNum];
                    individual.getChromosome()[geneNum] = !geneValue;
                }
            }

            individual.setFitness(fitnessFunction.calculate(individual));
        }
    }

}
