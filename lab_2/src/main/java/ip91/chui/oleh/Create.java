package ip91.chui.oleh;

public class Create extends Element {

  public Create(double delay) {
    super(delay);
  }

  @Override
  public void outAct() {
    super.outAct();
    super.setTnext(super.getTcurr() + super.getDelay());
    super.getNextElement().inAct();
  }


}
