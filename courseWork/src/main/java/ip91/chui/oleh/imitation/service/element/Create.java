package ip91.chui.oleh.imitation.service.element;

import ip91.chui.oleh.imitation.config.Config;

public class Create extends Element {

  private static final String CAR_MOVE_FROM_TO = "Car is going to move from %s to %s. %n";

  public Create(String name, double delay) {
    super(name, delay);
    super.setTcurr(0);
    super.setTnext(0);
  }

  @Override
  public void outAct() {
    super.outAct();
    super.setTnext(super.getTcurr() + super.getDelay());
    printCarMoveFromTo();
    super.getNextElement().inAct();
  }

  private void printCarMoveFromTo() {
    if (Config.LOGS_OFF) {
      return;
    }

    System.out.printf(CAR_MOVE_FROM_TO, this.getName(), super.getNextElement().getName());
  }

}
