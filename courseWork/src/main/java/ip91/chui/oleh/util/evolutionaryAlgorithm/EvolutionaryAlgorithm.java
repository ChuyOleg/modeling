package ip91.chui.oleh.util.evolutionaryAlgorithm;

import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.BackpackConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.SalesmanConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config;
import ip91.chui.oleh.util.evolutionaryAlgorithm.config.GenerationReplacementType;
import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.Crossover;
import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.FairPointCrossover;
import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.RandomPointCrossover;
import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.chromosomeController.BackpackChromosomeController;
import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.chromosomeController.ChromosomeController;
import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.chromosomeController.SalesmanChromosomeController;
import ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.chromosomeController.TrafficLightsChromosomeController;
import ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction.BackpackFitnessFunction;
import ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction.FitnessFunction;
import ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction.SalesmanFitnessFunction;
import ip91.chui.oleh.util.evolutionaryAlgorithm.fitnessFunction.TrafficLightsFitnessFunction;
import ip91.chui.oleh.util.evolutionaryAlgorithm.generationReplacement.AllOffspringIntoPopulationGenerationReplacement;
import ip91.chui.oleh.util.evolutionaryAlgorithm.generationReplacement.GenerationReplacement;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Result;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.RuntimeInfo;
import ip91.chui.oleh.util.evolutionaryAlgorithm.mutation.Mutation;
import ip91.chui.oleh.util.evolutionaryAlgorithm.mutation.OppositeBooleanValueMutation;
import ip91.chui.oleh.util.evolutionaryAlgorithm.mutation.PlusOrMinusMutation;
import ip91.chui.oleh.util.evolutionaryAlgorithm.mutation.SwapGenesMutation;
import ip91.chui.oleh.util.evolutionaryAlgorithm.selection.HalfPopulationSelection;
import ip91.chui.oleh.util.evolutionaryAlgorithm.selection.OneBestOneRandomSelection;
import ip91.chui.oleh.util.evolutionaryAlgorithm.selection.Selection;
import ip91.chui.oleh.util.evolutionaryAlgorithm.selection.TwoBestSelection;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class EvolutionaryAlgorithm {

    private final BackpackConditionData backpackConditionData;
    private final SalesmanConditionData salesmanConditionData;

    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;
    private GenerationReplacement generationReplacement;

    public void init() {
        FitnessFunction fitnessFunction = getFitnessFunction();
        ChromosomeController chromosomeController = getChromosomeController();
        Random random = new Random();

        initSelection(random);
        initCrossover(chromosomeController, random);
        initMutation(fitnessFunction, random);
        initGenerationReplacement();
    }

    public Result run(Population population, RuntimeInfo info) {
        sortPopulationBasedOnTaskType(population);
        int generationCounter = 0;

        while (generationCounter < Config.MAX_GENERATION_NUMBER && info.getBestIndividualNotChangeCounter() < Config.GENERATION_WITHOUT_CHANGING_LIMIT) {
            List<Individual> bestParents = selection.process(population);

            List<Individual> offspring = crossover.process(bestParents);

            mutation.process(offspring);

            generationReplacement.process(population, offspring);

            sortPopulationBasedOnTaskType(population);

            changeBestIndividualBasedOnTaskTypeIfPossible(population, info);

            generationCounter++;
        }

        return new Result(info.getBestIndividual(), generationCounter);
    }

    private FitnessFunction getFitnessFunction() {
        return switch (Config.TASK_NAME) {
            case BACKPACK -> new BackpackFitnessFunction(backpackConditionData);
            case SALESMAN -> new SalesmanFitnessFunction(salesmanConditionData);
            case TRAFFIC_LIGHTS -> new TrafficLightsFitnessFunction();
        };
    }

    private ChromosomeController getChromosomeController() {
        return switch (Config.TASK_NAME) {
            case BACKPACK -> new BackpackChromosomeController();
            case SALESMAN -> new SalesmanChromosomeController();
            case TRAFFIC_LIGHTS -> new TrafficLightsChromosomeController();
        };
    }

    private void initSelection(Random random) {
        switch (Config.SELECTION_TYPE) {
            case HALF_POPULATION -> selection = new HalfPopulationSelection();
            case ONE_BEST_ONE_RANDOM -> selection = new OneBestOneRandomSelection(random);
            case TWO_BEST -> selection = new TwoBestSelection();
        }
    }

    private void initCrossover(ChromosomeController chromosomeController, Random random) {
        switch (Config.CROSSOVER_TYPE) {
            case FAIR_POINT -> crossover = new FairPointCrossover(chromosomeController);
            case RANDOM_POINT -> crossover = new RandomPointCrossover(chromosomeController, random);
        }
    }

    private void initMutation(FitnessFunction fitnessFunction, Random random) {
        switch (Config.MUTATION_TYPE) {
            case SWAP_GENES -> mutation = new SwapGenesMutation(fitnessFunction, random);
            case OPPOSITE_VALUE -> mutation = new OppositeBooleanValueMutation(fitnessFunction, random);
            case PLUS_MINUS -> mutation = new PlusOrMinusMutation(fitnessFunction, random);
        }
    }

    private void initGenerationReplacement() {
        if (Config.GENERATION_REPLACEMENT_TYPE == GenerationReplacementType.All_OFFSPRING_INTO_POPULATION) {
            generationReplacement = new AllOffspringIntoPopulationGenerationReplacement();
        }
    }

    private void sortPopulationBasedOnTaskType(Population population) {
        switch (Config.TASK_TYPE) {
            case MAXIMIZATION -> population.getIndividuals().sort(Comparator.comparingInt(Individual::getFitness));
            case MINIMIZATION -> population.getIndividuals().sort((i1, i2) -> i2.getFitness() - i1.getFitness());
        }
    }

    private void changeBestIndividualBasedOnTaskTypeIfPossible(Population population, RuntimeInfo info) {
        Individual bestInPopulation = population.getIndividuals().get(population.getIndividuals().size() - 1);

        switch (Config.TASK_TYPE) {
            case MAXIMIZATION ->
                changeBestIndividualIfPossible(info, bestInPopulation, (ind) -> ind.getFitness() > info.getBestIndividual().getFitness());
            case MINIMIZATION ->
                changeBestIndividualIfPossible(info, bestInPopulation, (ind) -> ind.getFitness() < info.getBestIndividual().getFitness());
        }
    }

    private void changeBestIndividualIfPossible(RuntimeInfo info, Individual individual, Predicate<Individual> predicate) {
        if (info.getBestIndividual() == null || predicate.test(individual)) {
            info.setBestIndividual(individual);
            info.setBestIndividualNotChangeCounter(0);
        } else {
            info.setBestIndividualNotChangeCounter(info.getBestIndividualNotChangeCounter() + 1);
        }
    }

}
