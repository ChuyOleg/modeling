package ip91.chui.oleh.imitation;

import ip91.chui.oleh.imitation.model.Distribution;
import ip91.chui.oleh.imitation.model.trafficLights.TrafficLights;
import ip91.chui.oleh.imitation.model.trafficLights.TrafficLightsNet;
import ip91.chui.oleh.imitation.model.trafficLights.TrafficLightsState;
import ip91.chui.oleh.imitation.service.Model;
import ip91.chui.oleh.imitation.service.element.Create;
import ip91.chui.oleh.imitation.service.element.Element;
import ip91.chui.oleh.imitation.service.element.Exit;
import ip91.chui.oleh.imitation.service.element.Process;

import java.util.List;

import static ip91.chui.oleh.imitation.config.Config.*;

public class ModelBuilder {

  private ModelBuilder() {}

  public static Model build(double green_1, double green_2) {
    Create creator_1 = new Create(CREATOR_1_NAME, CREATOR_1_DELAY);
    Create creator_2 = new Create(CREATOR_2_NAME, CREATOR_2_DELAY);

    TrafficLights trafficLights_1 = new TrafficLights(TrafficLightsState.GREEN, green_1);
    TrafficLights trafficLights_2 = new TrafficLights(TrafficLightsState.RED, green_2);
    TrafficLightsNet trafficLightsNet = new TrafficLightsNet(trafficLights_1, trafficLights_2);

    Process process_1 = new Process(PROCESS_1_NAME, MAX_QUEUE, PROCESS_1_DELAY, trafficLights_1);
    Process process_2 = new Process(PROCESS_2_NAME, MAX_QUEUE, PROCESS_2_DELAY, trafficLights_2);

    Exit exit = new Exit(EXIT_NAME);

    process_1.setDistribution(Distribution.MEAN_VALUE);
    process_2.setDistribution(Distribution.MEAN_VALUE);

    trafficLights_1.setProcess(process_1);
    trafficLights_2.setProcess(process_2);

    creator_1.setNextElement(process_1);
    creator_2.setNextElement(process_2);

    process_1.setNextElement(exit);
    process_2.setNextElement(exit);

    List<Element> list = List.of(creator_1, creator_2, process_1, process_2, exit);
    return new Model(list, trafficLightsNet);
  }

}
