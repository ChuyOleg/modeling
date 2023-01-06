package ip91.chui.oleh.imitation.model.trafficLights;

import ip91.chui.oleh.imitation.service.element.Process;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrafficLights {

  private TrafficLightsState state;
  private Process process;
  private double workingDelay;

  public TrafficLights(TrafficLightsState trafficLightsState, double workingDelay) {
    this.state = trafficLightsState;
    this.workingDelay = workingDelay;
  }

}
