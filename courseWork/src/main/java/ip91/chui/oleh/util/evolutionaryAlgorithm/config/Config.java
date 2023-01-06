package ip91.chui.oleh.util.evolutionaryAlgorithm.config;

public class Config {

    private Config() {}

    // BACKPACK vs SALESMAN vs TRAFFIC_LIGHTS (new)
    public static final TaskName TASK_NAME = TaskName.TRAFFIC_LIGHTS;
    // MAXIMIZATION vs MINIMIZATION
    public static final TaskType TASK_TYPE = TaskType.MINIMIZATION;
    public static final int TEST_PERFORMANCE_ITERATION_NUM = 3;

    // RANDOM vs FROM_FILE
    public static final ConditionDataType CONDITION_DATA_TYPE = ConditionDataType.RANDOM;
    public static final String BACKPACK_CONDITION_DATA_FILE_NAME = "src/main/resources/backpackConditionData_1.txt";
    public static final String SALESMAN_CONDITION_DATA_FILE_NAME = "src/main/resources/salesmanConditionData_1.txt";

    // PARALLEL vs SEQUENCE
    public static final AlgorithmType ALGORITHM_TYPE = AlgorithmType.SEQUENCE;
    public static final int THREAD_NUMBER = 4; // this value is omitted if ALGORITHM_TYPE = AlgorithmType.SEQUENCE

    // TWO_BEST vs ONE_BEST_ONE_RANDOM vs HALF_POPULATION
    public static final SelectionType SELECTION_TYPE = SelectionType.HALF_POPULATION;
    // FAIR_POINT vs RANDOM_POINT
    public static final CrossoverType CROSSOVER_TYPE = CrossoverType.FAIR_POINT;
    // BACKPACK -> OPPOSITE_VALUE vs SWAP_GENES; SALESMAN -> only SWAP_GENES
    public static final MutationType MUTATION_TYPE = MutationType.PLUS_MINUS;
    // All_OFFSPRING_INTO_POPULATION
    public static final GenerationReplacementType GENERATION_REPLACEMENT_TYPE = GenerationReplacementType.All_OFFSPRING_INTO_POPULATION;

    public static final int MAX_GENERATION_NUMBER = 500;
    public static final int GENERATION_WITHOUT_CHANGING_LIMIT = 150;

    public static final int POPULATION_SIZE = 50;
    // IF MUTATION_MEASURE = 100, MUTATION_PERCENTAGE = 1 then chance for mutation = 1/100;
    public static final int MUTATION_MEASURE = 100;
    public static final int MUTATION_PERCENTAGE = 50;
    public static final double PLUS_MINUS_MUTATION_VALUE = 0.5;

    /* Backpack Configuration */
    public static final int BACKPACK_THING_COUNT = 300;
    public static final int THING_MIN_WEIGHT = 1;
    public static final int THING_MAX_WEIGHT = 2000;
    public static final int BACKPACK_MAX_WEIGHT = BACKPACK_THING_COUNT * (THING_MAX_WEIGHT + THING_MIN_WEIGHT) / 3;
    public static final int THING_MIN_PRICE = 1;
    public static final int THING_MAX_PRICE = 5000;
    //*****************************

    /* Salesman Configuration */
    public static final int SALESMAN_CITY_COUNT = 50;
    public static final int SALESMAN_MIN_LENGTH = 2;
    public static final int SALESMAN_MAX_LENGTH = 5000;
    //*****************************

    /* Traffic Lights Configuration */
    public static final int GREEN_TRAFFIC_LIGHTS_MAX_DELAY = 200;
    public static final int FIND_AVERAGE_MEAN_WAITING_TINE_ITERATION_COUNT = 5;
    public static final double IMITATION_TIME = 5000;
    //*****************************

}
