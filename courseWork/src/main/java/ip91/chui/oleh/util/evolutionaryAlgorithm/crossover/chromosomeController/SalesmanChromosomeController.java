package ip91.chui.oleh.util.evolutionaryAlgorithm.crossover.chromosomeController;

import ip91.chui.oleh.util.evolutionaryAlgorithm.model.Individual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SalesmanChromosomeController implements ChromosomeController {

    @Override
    public Object[] createUsingPointCrossover(Individual parent1, Individual parent2, int point) {
        List<Object> firstPart = Arrays.asList(parent1.getChromosome()).subList(0, point);
        List<Object> secondPart = Arrays.asList(parent2.getChromosome()).subList(point, parent2.getChromosome().length);
        LinkedList<Object> freeGenes = Arrays.stream(parent1.getChromosome())
                .filter(gene -> !firstPart.contains(gene) && !secondPart.contains(gene))
                .collect(Collectors.toCollection(LinkedList::new));

        List<Object> child = new ArrayList<>(firstPart);
        for (Object gene : secondPart) {
            if (firstPart.contains(gene)) {
                child.add(freeGenes.poll());
            } else {
                child.add(gene);
            }
        }

        return child.toArray();
    }

}
