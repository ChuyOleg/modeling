package ip91.chui.oleh.util.evolutionaryAlgorithm.mutation;

import ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config;
import ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class PlusOrMinusMutation implements Mutation {

  private static final String HEADS = "Heads";
  private static final String TAILS = "Tails";

  private final FitnessFunction fitnessFunction;
  private final Random random;

  @Override
  public void process(List<Individual> individuals) {
    for (Individual individual : individuals) {
      for (int genNum = 0; genNum < individual.getChromosome().length; genNum++) {
        final boolean chance = random.nextInt(Config.MUTATION_MEASURE) < Config.MUTATION_PERCENTAGE;
        if (chance) {
          double geneValue = (double) individual.getChromosome()[genNum];
          final String coinSide = flipACoin();

          switch (coinSide) {
            case HEADS -> individual.getChromosome()[genNum] = geneValue + Config.PLUS_MINUS_MUTATION_VALUE;
            case TAILS -> individual.getChromosome()[genNum] = geneValue - Config.PLUS_MINUS_MUTATION_VALUE;
            default -> throw new RuntimeException();
          }
        }

      }

      individual.setFitness(fitnessFunction.calculate(individual));
    }
  }

  private String flipACoin() {
    final double randomValue = Math.random();
    if (randomValue < 0.5) {
      return HEADS;
    } else {
      return TAILS;
    }
  }

}
