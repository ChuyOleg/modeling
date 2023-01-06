package ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction;

import ip91.chui.oleh.imitation.ModelBuilder;
import ip91.chui.oleh.imitation.service.Model;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;

import static ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config.*;

public class TrafficLightsFitnessFunction implements FitnessFunction {

  @Override
  public int calculate(Individual individual) {
    final double green_1 = (double) individual.getChromosome()[0];
    final double green_2 = (double) individual.getChromosome()[1];
    double meanWaitingTimeSum = 0;

    for (int i = 0; i < FIND_AVERAGE_MEAN_WAITING_TINE_ITERATION_COUNT; i++) {
      Model model = ModelBuilder.build(green_1, green_2);
      meanWaitingTimeSum += model.simulate(IMITATION_TIME);
    }

    return (int) Math.ceil(meanWaitingTimeSum / FIND_AVERAGE_MEAN_WAITING_TINE_ITERATION_COUNT);
  }

}
