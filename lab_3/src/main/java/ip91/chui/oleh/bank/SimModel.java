package ip91.chui.oleh.bank;

import ip91.chui.oleh.bank.model.ElementChancePair;
import ip91.chui.oleh.bank.service.Model;
import ip91.chui.oleh.bank.service.element.Create;
import ip91.chui.oleh.bank.service.element.Device;
import ip91.chui.oleh.bank.service.element.Element;
import ip91.chui.oleh.bank.service.element.Exit;
import ip91.chui.oleh.bank.service.element.Process;

import java.util.List;

import static ip91.chui.oleh.bank.config.Config.IMITATION_TIME;

public class SimModel {

  private static final double MAX_CHANCE = 1.0;
  private static final double NO_CHANCE = -1.0;
  private static final double CREATOR_DELAY = 0.5;
  private static final double FIRST_CLIENT_TIME = 0.1;
  private static final double CASHIER_DELAY = 1;
  private static final double CASHIER_DELAY_DEV = 0.3;
  private static final int CASHIER_MAX_QUEUE = 3;
  private static final int CASHIER_START_QUEUE = 2;

  public static void main(String[] args) {
    Create creator = new Create("CREATOR", CREATOR_DELAY);
    Process process_1 = new Process("CASHIER_1",  CASHIER_MAX_QUEUE);
    Process process_2 = new Process("CASHIER_2", CASHIER_MAX_QUEUE);
    Exit exit = new Exit("Exit");

    List<Device> p1Devices = List.of(
        new Device("CASHIER_1_DEVICE_1", CASHIER_DELAY, CASHIER_DELAY_DEV, process_1)
    );
    List<Device> p2Devices = List.of(
        new Device("CASHIER_2_DEVICE_1", CASHIER_DELAY, CASHIER_DELAY_DEV, process_2)
    );

    process_1.setDevices(p1Devices);
    process_2.setDevices(p2Devices);

    creator.setNextElements(List.of(
        new ElementChancePair(process_1, NO_CHANCE),
        new ElementChancePair(process_2, NO_CHANCE))
    );
    process_1.setNextElements(List.of(
        new ElementChancePair(exit, MAX_CHANCE))
    );
    process_2.setNextElements(List.of(
        new ElementChancePair(exit, MAX_CHANCE)
    ));

    // прибуття першого клієнта заплановано на момент часу 0,1 од. часу;
    creator.setTnext(FIRST_CLIENT_TIME);

    // у кожній черзі очікують по два автомобіля.
    process_1.setQueue(CASHIER_START_QUEUE);
    process_2.setQueue(CASHIER_START_QUEUE);

    // обидва касири зайняті
    process_1.inAct();
    process_2.inAct();

    List<Element> list = List.of(creator, process_1, process_2, exit);
    Model model = new Model(list);
    model.simulate(IMITATION_TIME);
  }

}
