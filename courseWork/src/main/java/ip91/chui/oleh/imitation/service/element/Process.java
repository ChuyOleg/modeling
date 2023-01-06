package ip91.chui.oleh.imitation.service.element;

import ip91.chui.oleh.imitation.config.Config;
import ip91.chui.oleh.imitation.model.trafficLights.TrafficLights;
import lombok.Getter;
import lombok.Setter;

import static ip91.chui.oleh.imitation.model.trafficLights.TrafficLightsState.GREEN;

@Getter
@Setter
public class Process extends Element {

  private static final String MAX_QUEUE_MSG = "Max_Queue: ";
  private static final String QUEUE_MSG = "Queue: ";
  private static final String FAILURE_MSG = "Failure: ";
  private static final String MEAN_LENGTH_OF_QUEUE_MSG = "Mean length of queue = ";
  private static final String MEAN_WAITING_TIME = "\n   Mean waiting time of process = ";
  private static final String MEAN_LOAD_MSG = "\n   Mean load of process = ";
  private static final String FAILURE_PROBABILITY_MSG = "\n   Failure probability = ";
  private static final String DEVICE_HAS_BEEN_CHOSEN = "Device: <%s> has been chosen%n";
  private static final String EMPTY_DEVICE_NOT_FOUND = "Empty device hasn't been found";
  private static final String CAR_DRIVE_TRAFFIC_LIGHTS = "The car has driven traffic lights.";
  private static final String CAR_DRIVE_TRAFFIC_LIGHTS_WITHOUT_DELAY = "The car has driven traffic lights without delay.";
  private static final String CAR_FINISH_ROAD = "The car has entered the traffic lane.";
  private static final String CAR_MOVE_TO_QUEUE = "The car has moved to queue.";
  private static final String CAR_GONNA_MOVE_FROM_QUEUE = "Another car is going to move from queue to traffic lane.";
  private static final String START_WORK_AFTER_BREAK = "%s has started working after break.%n";
  private static final String DELIMITER = ": ";

  private final TrafficLights trafficLights;
  private int queue;
  private int maxQueue;
  private int failure;
  private double meanQueue;
  private double meanLoad;

  public Process(String name, int maxQueue, double delay, TrafficLights trafficLights) {
    super(name, delay);
    this.maxQueue = maxQueue;
    this.trafficLights = trafficLights;
  }

  @Override
  public void inAct() {
    if (super.getState() == 0 && trafficLights.getState().equals(GREEN)) {
      super.setState(1);
      if (getQueue() == 0) {
        super.setTnext(super.getTcurr());
      } else {
        super.setTnext(super.getTcurr() + super.getDelay());
      }
    } else {
      if (getQueue() < getMaxQueue()) {
        setQueue(getQueue() + 1);
        printLnMsg(this.getName() + DELIMITER + CAR_MOVE_TO_QUEUE);
      } else {
        failure++;
      }
    }
  }

  @Override
  public void outAct() {
    super.outAct();
    super.setTnext(Double.MAX_VALUE);
    super.setState(0);
    printLnMsg(this.getName() + DELIMITER + CAR_FINISH_ROAD);

    if (this.getQueue() > 0 && trafficLights.getState().equals(GREEN)) {
      printLnMsg(this.getName() + DELIMITER + CAR_GONNA_MOVE_FROM_QUEUE);
      this.setQueue(this.getQueue() - 1);
      super.setState(1);
      super.setTnext(super.getTcurr() + super.getDelay());
    }

    this.getNextElement().inAct();
  }

  public void triggerQueue() {
    if (super.getState() == 0 && this.getQueue() > 0 && trafficLights.getState().equals(GREEN)) {
      this.setQueue(this.getQueue() - 1);
      super.setState(1);
      super.setTnext(super.getTcurr() + super.getDelay());
      printStartWorkAfterBreakMsg();
    }
  }

  @Override
  public void printInfo() {
    super.printInfo();
    System.out.println(
        SUB_DESC_START + MAX_QUEUE_MSG + this.getMaxQueue() + VERTICAL_LINE +
        QUEUE_MSG + this.getQueue() + VERTICAL_LINE +
        FAILURE_MSG + this.getFailure()
    );
  }

  @Override
  public void printResult() {
    super.printResult();
    System.out.println(
        SUB_DESC_START + MEAN_LENGTH_OF_QUEUE_MSG + (this.meanQueue / super.getTcurr()) +
        MEAN_WAITING_TIME + (this.meanQueue / (super.getQuantity() + this.getQueue())) +
        MEAN_LOAD_MSG + (this.meanLoad / super.getTcurr())
    );
  }

  @Override
  public void doStatistics(double delta) {
    meanQueue = getMeanQueue() + queue * delta;
    meanLoad = getMeanLoad() + super.getState() * delta;
  }

  private void printLnMsg(String msg) {
    if (Config.LOGS_OFF) {
      return;
    }

    System.out.println(msg);
  }

  private void printStartWorkAfterBreakMsg() {
    if (Config.LOGS_OFF) {
      return;
    }

    System.out.printf(START_WORK_AFTER_BREAK, this.getName());
  }

}
