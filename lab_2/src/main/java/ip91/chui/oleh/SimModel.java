package ip91.chui.oleh;

import ip91.chui.oleh.model.ElementChancePair;

import java.util.List;

import static ip91.chui.oleh.config.Config.*;

public class SimModel {

  public static void main(String[] args) {
    Create c = new Create("CREATOR", 2.0);

    Process p1 = new Process("PROCESS_1",  5);
    Process p2 = new Process("PROCESS_2", 3);
    Process p3 = new Process("PROCESS_3", 0);
    Exit exit = new Exit("Exit");

    List<Device> p1Devices = List.of(
        new Device("PROCESS_1_DEVICE_1", 3.0, p1),
        new Device("PROCESS_1_DEVICE_2", 3.0, p1)
    );
    List<Device> p2Devices = List.of(new Device("PROCESS_2_DEVICE_1", 2.0, p2));
    List<Device> p3Devices = List.of(
        new Device("PROCESS_3_DEVICE_1", 3.0, p3),
        new Device("PROCESS_3_DEVICE_2", 3.0, p3)
    );

    p1.setDevices(p1Devices);
    p2.setDevices(p2Devices);
    p3.setDevices(p3Devices);

    c.setNextElements(List.of(new ElementChancePair(p1, 1.0)));
    p1.setNextElements(List.of(new ElementChancePair(p2, 1.0)));
    p2.setNextElements(List.of(
        new ElementChancePair(p1, 0.3),
        new ElementChancePair(p3, 0.7)
    ));
    p3.setNextElements(List.of(new ElementChancePair(exit, 1.0)));

    List<Element> list = List.of(c, p1, p2, p3, exit);
    Model model = new Model(list);
    model.simulate(IMITATION_TIME);
  }

}
