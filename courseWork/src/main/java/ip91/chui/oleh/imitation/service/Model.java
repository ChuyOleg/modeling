package ip91.chui.oleh.imitation.service;

import ip91.chui.oleh.imitation.config.Config;
import ip91.chui.oleh.imitation.service.element.Process;
import ip91.chui.oleh.imitation.model.trafficLights.TrafficLightsNet;
import ip91.chui.oleh.imitation.service.element.Element;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Model {

  private static final String RESULT_TITLE = "\n_____________RESULT_____________";
  private static final String TIME_EVENT_MSG = "\nEvent time for ";
  private static final String TIME_MSG = ", time = ";
  private static final String FINAL_MEAN_WAITING_TIME_MSG = "General mean waiting time = ";

  private final List<Element> list;
  private final TrafficLightsNet trafficLightsNet;
  private double tnext, tcurr;
  private Element event;

  public double simulate(double time) {
    while (tcurr < time) {
      tnext = Double.MAX_VALUE;

      list.forEach(el -> {
        if (el.getTnext() < tnext) {
          tnext = el.getTnext();
          event = el;
        }
      });

      if (trafficLightsNet.getTNext() <= tnext) {
        tnext = trafficLightsNet.getTNext();
        list.forEach(el -> el.doStatistics(tnext - tcurr));
        tcurr = tnext;
        list.forEach(el -> el.setTcurr(tcurr));
        trafficLightsNet.changeState();
        continue;
      }

      printCurrentEventMsg();

      trafficLightsNet.printState();

      list.forEach(el -> el.doStatistics(tnext - tcurr));
      tcurr = tnext;
      list.forEach(el -> el.setTcurr(tcurr));

      event.outAct();
      list.stream().filter(el -> el.getTnext() == tcurr).forEach(Element::outAct);

      printInfo();
    }

    final double meanWaitingTime = calculateMeanWaitingTime();
    printResult(meanWaitingTime);

    return meanWaitingTime;
  }

  private void printInfo() {
    if (Config.LOGS_OFF) {
      return;
    }

    list.forEach(Element::printInfo);
  }

  private void printResult(double meanWaitingTime) {
    if (Config.LOGS_OFF) {
      return;
    }

    System.out.println(RESULT_TITLE);
    list.forEach(Element::printResult);
    System.out.println(FINAL_MEAN_WAITING_TIME_MSG + meanWaitingTime);
  }

  private void printCurrentEventMsg() {
    if (Config.LOGS_OFF) {
      return;
    }

    System.out.println(TIME_EVENT_MSG + event.getName() + TIME_MSG + tnext);
  }

  private double calculateMeanWaitingTime() {
    return list.stream()
        .filter(el -> el.getClass().equals(Process.class))
        .map(el -> (Process) el)
        .map(process -> process.getMeanQueue() / (process.getQuantity() + process.getQueue()))
        .reduce(0.0, Double::sum);
  }

}
