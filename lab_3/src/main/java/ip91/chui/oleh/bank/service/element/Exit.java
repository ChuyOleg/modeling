package ip91.chui.oleh.bank.service.element;

public class Exit extends Element {

  private static final String TOKEN_COMPLETE_CYCLE = "The Car has been served";

  public Exit(String name) {
    super(name, 0);
  }

  @Override
  public void inAct() {
    super.inAct();
    super.setQuantity(super.getQuantity() + 1);
    System.out.println(TOKEN_COMPLETE_CYCLE);
  }

  @Override
  public void outAct() {}

  @Override
  public void printInfo() {
    super.printResult();
  }
}
