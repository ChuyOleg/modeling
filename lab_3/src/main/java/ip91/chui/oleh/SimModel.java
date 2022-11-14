package ip91.chui.oleh;

import ip91.chui.oleh.model.ElementChancePair;
import ip91.chui.oleh.service.Model;
import ip91.chui.oleh.service.element.Process;
import ip91.chui.oleh.service.element.*;

import java.util.List;

import static ip91.chui.oleh.config.Config.IMITATION_TIME;

public class SimModel {

  public static void main(String[] args) {
    Create creator = new Create("CREATOR", 3);
    Process process_1 = new Process("PROCESS_1",  6);
    Process process_2 = new Process("PROCESS_2", 4);
    Process process_3 = new Process("PROCESS_3", 6);
    Exit exit = new Exit("Exit");

    List<Device> p1Devices = List.of(
        new Device("PROCESS_1_DEVICE_1", 4.0, process_1)
    );
    List<Device> p2Devices = List.of(
        new Device("PROCESS_2_DEVICE_1", 6.0, process_2)
    );
    List<Device> p3Devices = List.of(
        new Device("PROCESS_3_DEVICE_1", 3, process_3)
    );

    process_1.setDevices(p1Devices);
    process_2.setDevices(p2Devices);
    process_3.setDevices(p3Devices);

    creator.setNextElements(List.of(
        new ElementChancePair(process_1, 1.0))
    );
    process_1.setNextElements(List.of(
        new ElementChancePair(process_2, 1.0))
    );
    process_2.setNextElements(List.of(
        new ElementChancePair(process_3, 1.0)
    ));
    process_3.setNextElements(List.of(
        new ElementChancePair(exit, 1.0))
    );

    List<Element> list = List.of(creator, process_1, process_2, process_3, exit);
    Model model = new Model(list);
    model.simulate(IMITATION_TIME);
  }

}
