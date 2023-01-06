package ip91.chui.oleh.imitation.config;

public class Config {

  private Config() {}

  public static final double IMITATION_TIME = 5000;

  public static final double GREEN_1_BREAK = 30;
  public static final double GREEN_2_BREAK = 40;
  public static final double RED_BREAK = 55;

  public static final int MAX_QUEUE = Integer.MAX_VALUE;

  public static final String CREATOR_1_NAME = "CREATOR_1";
  public static final double CREATOR_1_DELAY = 12;

  public static final String CREATOR_2_NAME = "CREATOR_2";
  public static final double CREATOR_2_DELAY = 9;

  public static final String PROCESS_1_NAME = "TRAFFIC_LIGHTS_1";
  public static final double PROCESS_1_DELAY = 2;

  public static final String PROCESS_2_NAME = "TRAFFIC_LIGHTS_2";
  public static final double PROCESS_2_DELAY = 2;

  public static final String EXIT_NAME = "Exit";

  public static final boolean LOGS_OFF = false;

  // Evolutionary Algorithm __Info__ (short description of selected parameters for our task)
  // class ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config - contains parameters, they can be changed based on ENUM values;

  // Task Type: MINIMIZATION.
  // The main goal for this coursework is to find the best green delays for the lowest value of waiting time,
  // so type of our task is minimization.

  // Selection: HALF_POPULATION.
  // There are 3 options to select: HALF_POPULATION, ONE_BEST_ONE_RANDOM, TWO_BEST.
  // The HALF_POPULATION type means that we select the better part (1/2) of parents (based on fitness value)
  // on every iteration. This type gives us more children after crossover comparing with other Selection types.

  // Crossover: FAIR_POINT.
  // There are only 2 options: FAIR_POINT and RANDOM_POINT.
  // Since the chromosome contains only 2 genes (green_1, green_2), available types of
  // crossover are equal.

  // Mutation: PLUS_MINUS (50% chance).
  // TYPES: PLUS_MINUS, OPPOSITE_GENES, SWAP_VALUES.
  // OPPOSITE_GENES is a great choice if a chromosome contains boolean values. Doesn't fit for our task.
  // SWAP_VALUES - our chromosome has only 2 genes so this type isn't good for our task.
  // PLUS_MINUS - adds / subtracts some value (PLUS_MINUS_MUTATION_VALUE = 0.5 for our task) to / from the value of gene.

  // GenerationReplacement: ALL_OFFSPRING_INTO_POPULATION.
  // The only choice. Means that offspring substitutes the worse individuals of the population

  // GREEN_TRAFFIC_LIGHTS_MAX_DELAY = 200;
  // The max value of the green delay when generating the initial population

  // FIND_AVERAGE_MEAN_WAITING_TIME_ITERATION_COUNT = 5;
  // The number of iterations for fitting individual (then find average of them)

  // IMITATION_TIME = 5000;
  // The imitation time (only for evolutionary algorithm).

}
