package ip91.chui.oleh;


import ip91.chui.oleh.util.evolutionaryAlgorithm.ParallelEvolutionaryAlgorithm;
import ip91.chui.oleh.util.evolutionaryAlgorithm.SequenceEvolutionaryAlgorithm;
import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.BackpackConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.ConditionDataGenerator;
import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.SalesmanConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.config.AlgorithmType;
import ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config;
import ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction.TrafficLightsFitnessFunction;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Result;
import ip91.chui.oleh.util.evolutionaryAlgorithm.populationGenerator.BackpackPopulationGenerator;
import ip91.chui.oleh.util.evolutionaryAlgorithm.populationGenerator.SalesmanPopulationGenerator;
import ip91.chui.oleh.util.evolutionaryAlgorithm.populationGenerator.TrafficLightsPopulationGenerator;
import ip91.chui.oleh.util.evolutionaryAlgorithm.util.CustomPrinter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Experiment {

    private static BackpackConditionData backpackConditionData;
    private static SalesmanConditionData salesmanConditionData;

    private static Population population;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        initConditionData();
        initPopulation(new Random());
        printAlgorithmInfo();

        Result result = warmUpMachine();

        if (Config.TEST_PERFORMANCE_ITERATION_NUM > 0) {
            BigDecimal averageTime = testPerformance();
            System.out.println("Average execution time for " + Config.TEST_PERFORMANCE_ITERATION_NUM + " iteration: " + (averageTime) +  " ms" );
        }

        System.out.println("Best Individual's Chromosome: " + Arrays.toString(result.getBestIndividual().getChromosome()));
        System.out.println("Best fitness: " + result.getBestIndividual().getFitness());
        System.out.println("Generation: " + result.getGeneration());
    }

    private static Result warmUpMachine() throws ExecutionException, InterruptedException {
        return switch (Config.ALGORITHM_TYPE) {
            case SEQUENCE -> runSequenceAlgorithm();
            case PARALLEL -> runParallelAlgorithm();
        };
    }

    private static BigDecimal testPerformance() throws ExecutionException, InterruptedException {
        BigDecimal fullTime = BigDecimal.valueOf(0);

        long start;
        long finish;
        for (int iteration = 0; iteration < Config.TEST_PERFORMANCE_ITERATION_NUM; iteration++) {
            switch (Config.ALGORITHM_TYPE) {
                case SEQUENCE -> {
                    start = System.currentTimeMillis();
                    runSequenceAlgorithm();
                    finish = System.currentTimeMillis();
                    fullTime = fullTime.add(BigDecimal.valueOf(finish - start));
                }
                case PARALLEL -> {
                    start = System.currentTimeMillis();
                    runParallelAlgorithm();
                    finish = System.currentTimeMillis();
                    fullTime = fullTime.add(BigDecimal.valueOf(finish - start));
                }
            }
        }

        return fullTime.divide(BigDecimal.valueOf(Config.TEST_PERFORMANCE_ITERATION_NUM), RoundingMode.CEILING);
    }

    private static Result runSequenceAlgorithm() {
        SequenceEvolutionaryAlgorithm sequenceEvolutionaryAlgorithm = new SequenceEvolutionaryAlgorithm(
                backpackConditionData, salesmanConditionData, createPopulationCopy(population)
        );
        return sequenceEvolutionaryAlgorithm.process();
    }

    private static Result runParallelAlgorithm() throws ExecutionException, InterruptedException {
        ParallelEvolutionaryAlgorithm parallelEvolutionaryAlgorithm = new ParallelEvolutionaryAlgorithm(
                backpackConditionData, salesmanConditionData, createPopulationCopy(population)
        );
        return parallelEvolutionaryAlgorithm.process();
    }

    private static void initConditionData() {
        switch (Config.TASK_NAME) {
            case BACKPACK:
                switch (Config.CONDITION_DATA_TYPE) {
                    case RANDOM -> backpackConditionData = ConditionDataGenerator.backpack();
                    case FROM_FILE -> backpackConditionData = CustomPrinter.readBackpackConditionDataFromFile();
                }
                break;
            case SALESMAN:
                switch (Config.CONDITION_DATA_TYPE) {
                    case RANDOM -> salesmanConditionData = ConditionDataGenerator.salesman();
                    case FROM_FILE -> salesmanConditionData = CustomPrinter.readSalesmanConditionDataFromFile();
                }
                break;
        }
    }

    private static void initPopulation(Random random) {
        switch (Config.TASK_NAME) {
            case BACKPACK -> population = new BackpackPopulationGenerator(backpackConditionData, random).generate();
            case SALESMAN -> population = new SalesmanPopulationGenerator(salesmanConditionData, random).generate();
            case TRAFFIC_LIGHTS -> population = new TrafficLightsPopulationGenerator(new TrafficLightsFitnessFunction(), random).generate();
        }
    }

    private static Population createPopulationCopy(Population population) {
        List<Individual> individualList = new ArrayList<>(population.getIndividuals());

        return new Population(individualList);
    }

    private static void printAlgorithmInfo() {
        final String algorithmType =  (Config.ALGORITHM_TYPE.equals(AlgorithmType.SEQUENCE)) ? "Sequence" : "Parallel Thread_Number = " + Config.THREAD_NUMBER;
        final String thingName = switch (Config.TASK_NAME) {
            case SALESMAN -> "cities";
            case BACKPACK -> "things";
            case TRAFFIC_LIGHTS -> "No Info";
        };
        final int thingCount = switch (Config.TASK_NAME) {
            case SALESMAN -> salesmanConditionData.getRoadMatrix().length;
            case BACKPACK -> backpackConditionData.getWeightTable().length;
            case TRAFFIC_LIGHTS -> 0;
        };

        System.out.printf(
            """
                %s: [data = %s | Max Generation = %d | Generation without changes limit = %d | Population size = %d | %s = %d]
                Algorithm type = %s\s
                selection = %s\s
                crossover = %s\s
                mutation = %s \s

                """,
                Config.TASK_NAME,
                Config.CONDITION_DATA_TYPE,
                Config.MAX_GENERATION_NUMBER,
                Config.GENERATION_WITHOUT_CHANGING_LIMIT,
                Config.POPULATION_SIZE,
                thingName,
                thingCount,
                algorithmType,
                Config.SELECTION_TYPE,
                Config.CROSSOVER_TYPE,
                Config.MUTATION_TYPE
        );
    }

}
