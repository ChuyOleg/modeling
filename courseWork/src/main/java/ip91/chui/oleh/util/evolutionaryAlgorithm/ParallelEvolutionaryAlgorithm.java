package ip91.chui.oleh.util.evolutionaryAlgorithm;

import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.BackpackConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData.SalesmanConditionData;
import ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Population;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Result;
import ip91.chui.oleh.util.evolutionaryAlgorithm.model.RuntimeInfo;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RequiredArgsConstructor
public class ParallelEvolutionaryAlgorithm {

    private final BackpackConditionData backpackConditionData;
    private final SalesmanConditionData salesmanConditionData;

    private final Population population;

    public Result process() throws InterruptedException, ExecutionException {
        EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithm(backpackConditionData, salesmanConditionData);
        evolutionaryAlgorithm.init();

        ExecutorService executorService = Executors.newFixedThreadPool(Config.THREAD_NUMBER);

        List<Callable<Result>> tasks = createTasks(evolutionaryAlgorithm);
        List<Future<Result>> futureList = executorService.invokeAll(tasks);

        Result result = fetchResultFromFutureList(futureList);

        executorService.shutdown();

        return result;
    }

    private List<Callable<Result>> createTasks(EvolutionaryAlgorithm algorithm) {
        List<Callable<Result>> tasks = new ArrayList<>();

        for (int i = 0; i < Config.THREAD_NUMBER; i++) {
            final int finalI = i;
            tasks.add(() -> algorithm.run(getPartOfPopulation(population, finalI), new RuntimeInfo(null, 0)));
        }

        return tasks;
    }

    private Population getPartOfPopulation(Population population, int num) {
        List<Individual> individuals = new ArrayList<>();

        int quantity = population.getIndividuals().size() / Config.THREAD_NUMBER;

        for (int index = num * quantity; index < (num + 1) * quantity; index++) {
            individuals.add(population.getIndividuals().get(index));
        }

        if (num == Config.THREAD_NUMBER - 1) {
            for (int index = (num + 1) * quantity; index < population.getIndividuals().size(); index++) {
                individuals.add(population.getIndividuals().get(index));
            }
        }

        return new Population(individuals);
    }

    private Result fetchResultFromFutureList(List<Future<Result>> futureList) throws ExecutionException, InterruptedException {
        List<Result> results = new ArrayList<>();
        Result bestResult = null;

        for (Future<Result> future : futureList) {
            results.add(future.get());
        }

        for (Result result : results) {
            if (activeResultIsBetter(result, bestResult)) {
                bestResult = result;
            }
        }

        return bestResult;
    }

    private boolean activeResultIsBetter(Result activeResult, Result bestResult) {
        if (bestResult == null) return true;
        return switch (Config.TASK_TYPE) {
            case MAXIMIZATION ->
                activeResult.getBestIndividual().getFitness() > bestResult.getBestIndividual().getFitness();
            case MINIMIZATION ->
                activeResult.getBestIndividual().getFitness() < bestResult.getBestIndividual().getFitness();
        };
    }

}
