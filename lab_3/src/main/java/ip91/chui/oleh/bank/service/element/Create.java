package ip91.chui.oleh.bank.service.element;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Create extends Element {

  private static final String CAR_LEFT_BANK = "The car has left the bank, no place";
  private static final String FAILURE_MSG = "Failure: ";
  private static final String FAILURE_PROBABILITY_MSG = "Failure probability = ";

  private int failure;

  public Create(String name, double delay) {
    super(name, delay);
    super.setTcurr(0);
    super.setTnext(0);
  }

  public void callNextElementInActByPriority() {
    Process cashier_1 = (Process) this.getNextElements().get(0).getElement();
    Process cashier_2 = (Process) this.getNextElements().get(1).getElement();

    if (cashier_1.getQueue() == cashier_1.getMaxQueue() && cashier_2.getQueue() == cashier_2.getMaxQueue()) {
      failure++;
      System.out.println(CAR_LEFT_BANK);
      return;
    }

    if (cashier_1.getQueue() <= cashier_2.getQueue()) {
      System.out.printf(TOKEN_MOVE_FROM_TO, this.getName(), cashier_1.getName());
      cashier_1.inAct();
    } else {
      System.out.printf(TOKEN_MOVE_FROM_TO, this.getName(), cashier_2.getName());
      cashier_2.inAct();
    }
  }

  @Override
  public void outAct() {
    super.outAct();
    super.setTnext(super.getTcurr() + super.getDelay());

    this.callNextElementInActByPriority();
  }

  @Override
  public void printInfo() {
    super.printInfo();

    System.out.println(SUB_DESC_START + FAILURE_MSG + this.getFailure());
  }

  @Override
  public void printResult() {
    super.printResult();



    System.out.println(SUB_DESC_START + FAILURE_PROBABILITY_MSG + (this.failure / (double) super.getQuantity()));
  }

}
