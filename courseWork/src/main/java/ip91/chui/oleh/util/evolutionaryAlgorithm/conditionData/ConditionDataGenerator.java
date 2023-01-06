package ip91.chui.oleh.util.evolutionaryAlgorithm.conditionData;

import ip91.chui.oleh.util.evolutionaryAlgorithm.config.Config;

import java.util.Random;

public class ConditionDataGenerator {

    public static BackpackConditionData backpack() {
        int[] weightTable = new int[Config.BACKPACK_THING_COUNT];
        int[] priceTable = new int[Config.BACKPACK_THING_COUNT];
        Random random = new Random();

        for (int thingNumber = 0; thingNumber < Config.BACKPACK_THING_COUNT; thingNumber++) {
            int randomWeight = random.nextInt(Config.THING_MAX_WEIGHT - Config.THING_MIN_WEIGHT + 1) + Config.THING_MIN_WEIGHT;
            int randomPrice = random.nextInt(Config.THING_MAX_PRICE - Config.THING_MIN_PRICE + 1) + Config.THING_MIN_PRICE;

            weightTable[thingNumber] = randomWeight;
            priceTable[thingNumber] = randomPrice;
        }

        return new BackpackConditionData(weightTable, priceTable, Config.BACKPACK_MAX_WEIGHT);
    }

    public static SalesmanConditionData salesman() {
        int[][] roadMatrix = new int[Config.SALESMAN_CITY_COUNT][Config.SALESMAN_CITY_COUNT];
        Random random = new Random();

        for (int source = 0; source < Config.SALESMAN_CITY_COUNT; source++) {
            for (int destination = 0; destination < Config.SALESMAN_CITY_COUNT; destination++) {
                if (source == destination) {
                    roadMatrix[source][destination] = Integer.MAX_VALUE;
                } else if (source < destination) {
                    int randomLength = random.nextInt(Config.SALESMAN_MAX_LENGTH - Config.SALESMAN_MIN_LENGTH + 1) + Config.SALESMAN_MIN_LENGTH;
                    roadMatrix[source][destination] = randomLength;
                } else {
                    roadMatrix[source][destination] = roadMatrix[destination][source];
                }
            }
        }

        return new SalesmanConditionData(roadMatrix);
    }

}
