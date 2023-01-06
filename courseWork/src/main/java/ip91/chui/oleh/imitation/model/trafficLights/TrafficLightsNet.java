package ip91.chui.oleh.imitation.model.trafficLights;

import ip91.chui.oleh.imitation.config.Config;
import lombok.Getter;

@Getter
public class TrafficLightsNet {

  private static final String STATE_ERR_MSG = "Invalid <state(%d)> of the TrafficLightsNet";
  private static final String STATE_MSG = "Traffic lights Net state: ";
  private static final String STATE_CHANGED_FROM_TO = "%nTraffic lights Net state has been changed from %s to %s at %.2f. Next change at %.2f.%n";
  private static final String STATE_0 = "(GREEN | RED)";
  private static final String STATE_1 = "(RED | RED)";
  private static final String STATE_2 = "(RED | GREEN)";
  private static final String STATE_3 = "(RED | RED)";

  private final TrafficLights trafficLights_1;
  private final TrafficLights trafficLights_2;
  /** field 'state' has 4 states = 0(GREEN_RED) | 1(RED_RED) 2(RED_GREEN) | 3(RED_RED) */
  private int state;
  private double tNext;

  /** Initial state is 0, so we assign delay value of trafficLights_1 to variable tNext */
  public TrafficLightsNet(TrafficLights trafficLights_1, TrafficLights trafficLights_2) {
    this.trafficLights_1 = trafficLights_1;
    this.trafficLights_2 = trafficLights_2;

    tNext = trafficLights_1.getWorkingDelay();
  }

  public void changeState() {
    switch (state) {
      case (0) -> {
        state = 1;
        trafficLights_1.setState(TrafficLightsState.RED);
        double tCurr = tNext;
        tNext += Config.RED_BREAK;
        printStateChangedFromToMsg(STATE_0, STATE_1, tCurr, tNext);
      }
      case (1) -> {
        state = 2;
        trafficLights_2.setState(TrafficLightsState.GREEN);
        double tCurr = tNext;
        tNext += trafficLights_2.getWorkingDelay();
        printStateChangedFromToMsg(STATE_1, STATE_2, tCurr, tNext);
        trafficLights_2.getProcess().triggerQueue();
      }
      case (2) -> {
        state = 3;
        trafficLights_2.setState(TrafficLightsState.RED);
        double tCurr = tNext;
        tNext += Config.RED_BREAK;
        printStateChangedFromToMsg(STATE_2, STATE_3, tCurr, tNext);
      }
      case (3) -> {
        state = 0;
        trafficLights_1.setState(TrafficLightsState.GREEN);
        double tCurr = tNext;
        tNext += trafficLights_1.getWorkingDelay();
        printStateChangedFromToMsg(STATE_3, STATE_0, tCurr, tNext);
        trafficLights_1.getProcess().triggerQueue();
      }
      default -> throw new RuntimeException(String.format(STATE_ERR_MSG, state));
    }
  }

  public void printState() {
    if (Config.LOGS_OFF) {
      return;
    }

    System.out.print(STATE_MSG);
    switch (state) {
      case 0 -> System.out.println(STATE_0);
      case 1 -> System.out.println(STATE_1);
      case 2 -> System.out.println(STATE_2);
      case 3 -> System.out.println(STATE_3);
      default -> throw new RuntimeException(String.format(STATE_ERR_MSG, state));
    }
  }

  private void printStateChangedFromToMsg(String from, String to, double tCurr, double tNext) {
    if (Config.LOGS_OFF) {
      return;
    }

    System.out.printf(STATE_CHANGED_FROM_TO, from, to, tCurr, tNext);
  }

}
