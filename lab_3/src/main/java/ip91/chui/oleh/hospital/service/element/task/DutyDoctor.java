package ip91.chui.oleh.hospital.service.element.task;

import ip91.chui.oleh.hospital.service.element.base.Device;
import ip91.chui.oleh.hospital.service.element.base.Process;

public class DutyDoctor extends Device {

    public DutyDoctor(String name, double delay, Process parentProcess) {
        super(name, delay, parentProcess);
    }

    @Override
    public double getDelay() {
        super.setDelayMean(this.getActivePatient().getPatientType().getMeanRegistrationTime());
        return super.getDelay();
    }
}
