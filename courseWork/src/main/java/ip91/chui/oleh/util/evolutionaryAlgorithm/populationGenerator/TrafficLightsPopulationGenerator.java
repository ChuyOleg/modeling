package ip91.chui.oleh.util.evolutionaryAlgorithm.populationGenerator;

import ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config;
import ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction.TrafficLightsFitnessFunction;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Getter
public class TrafficLightsPopulationGenerator implements PopulationGenerator {

  /** There are only 2 positions: delays for TrafficLights 1 and 2 */
  private static final int CHROMOSOME_SIZE = 2;

  private final TrafficLightsFitnessFunction fitnessFunction;
  private final Random random;

  @Override
  public Population generate() {
    List<Individual> individuals = new ArrayList<>(Config.POPULATION_SIZE);

    for (int individualNum = 0; individualNum < Config.POPULATION_SIZE; individualNum++) {
      Object[] chromosome = new Object[CHROMOSOME_SIZE];

      for (int gene = 0; gene < CHROMOSOME_SIZE; gene++) {
        chromosome[gene] = random.nextDouble(Config.GREEN_TRAFFIC_LIGHTS_MAX_DELAY);
      }

      Individual individual = new Individual(chromosome);
      individual.setFitness(fitnessFunction.calculate(individual));
      individuals.add(individual);
    }

    return new Population(individuals);
  }

}
