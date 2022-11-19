package ip91.chui.oleh.hospital.service.element.base;

import ip91.chui.oleh.hospital.model.Distribution;
import ip91.chui.oleh.hospital.model.Patient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Device extends Element {

  private static final String COME_FROM_QUEUE_MSG = "Patient <%s> has moved to <%s> from Queue %n";
  private static final String MEAN_LOAD_MSG = "\n      Mean load of device = ";
  private static final String INFO_START = "   ";

  private Patient activePatient;
  private final Process parentProcess;
  private double meanLoad;

  public Device(String name, double delay, Process parentProcess) {
    super(name, delay);
    this.parentProcess = parentProcess;
  }

  public Device(String name, double delay, double delayDev, Process parentProcess) {
    this(name, delay, parentProcess);
    super.setDistribution(Distribution.UNIFORM);
    super.setDelayDev(delayDev);
  }

  public Device(String name, double delay, int erlangK, Process parentProcess) {
    this(name, delay, parentProcess);
    super.setDistribution(Distribution.ERLANG);
    super.setErlangK(erlangK);
  }

  @Override
  public void inAct(Patient patient) {
    super.inAct(patient);
    super.setState(1);
    super.setTnext(super.getTcurr() + this.getDelay());
  }

  @Override
  public void outAct() {
    super.outAct();
    super.setTnext(Double.MAX_VALUE);
    super.setState(0);
    super.setActivePatient(null);

    if (parentProcess.getQueue().size() > 0) {
      Patient patientFromQueue = parentProcess.reduceQueue();
      System.out.printf(COME_FROM_QUEUE_MSG, patientFromQueue.getPatientType(), this.getName());
      super.setActivePatient(patientFromQueue);
      super.setState(1);
      super.setTnext(super.getTcurr() + this.getDelay());
    }
  }

  @Override
  public void printInfo() {
    System.out.println(
        INFO_START + STAR + this.getName() + ARROW + STATE_MSG + this.getState() + VERTICAL_LINE +
        QUANTITY_MSG + this.getQuantity() + VERTICAL_LINE +
        T_NEXT_MSG + this.getTnext()
    );
  }

  @Override
  public void printResult() {
    System.out.println(
        INFO_START + STAR + this.getName() + ARROW + QUANTITY_MSG + this.getQuantity() +
        MEAN_LOAD_MSG + this.meanLoad / super.getTcurr()
    );
  }

  @Override
  public void doStatistics(double delta) {
    meanLoad = getMeanLoad() + super.getState() * delta;
  }

}
