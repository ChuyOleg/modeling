package ip91.chui.oleh.hospital;

import ip91.chui.oleh.hospital.model.ElementChancePair;
import ip91.chui.oleh.hospital.model.PatientChancePair;
import ip91.chui.oleh.hospital.model.PatientType;
import ip91.chui.oleh.hospital.service.Model;
import ip91.chui.oleh.hospital.service.element.base.Create;
import ip91.chui.oleh.hospital.service.element.base.Device;
import ip91.chui.oleh.hospital.service.element.base.Element;
import ip91.chui.oleh.hospital.service.element.base.Exit;
import ip91.chui.oleh.hospital.service.element.base.Process;
import ip91.chui.oleh.hospital.service.element.task.DutyDoctor;
import ip91.chui.oleh.hospital.service.element.task.LaboratoryProcess;
import ip91.chui.oleh.hospital.service.element.task.LaboratoryWalkingProcess;
import ip91.chui.oleh.hospital.service.element.task.ReceptionProcess;

import java.util.List;
import java.util.stream.IntStream;

import static ip91.chui.oleh.hospital.config.Config.IMITATION_TIME;

public class SimModel {

  private static final double PATIENT_TYPE_1_CHANCE = 0.5;
  private static final double PATIENT_TYPE_2_CHANCE = 0.1;
  private static final double PATIENT_TYPE_3_CHANCE = 0.4;
  private static final double CREATOR_MEAN_TIME = 15;
  private static final double WARD_WALKING_TIME_MIN = 3;
  private static final double WARD_WALKING_TIME_MAX = 8;
  private static final int LAB_WALKING_DEVICE_COUNT = 10;
  private static final double LAB_WALKING_TIME_MIN = 2;
  private static final double LAB_WALKING_TIME_MAX = 5;
  private static final double REGISTRY_TIME_MEAN = 4.5;
  private static final int REGISTRY_ERLANG_K = 3;
  private static final double LAB_EXAMINATION_TIME_MEAN = 4;
  private static final int LAB_EXAMINATION_ERLANG_K = 2;
  private static final double MAX_CHANCE = 1;
  private static final double NO_CHANCE = -1;
  private static final double NO_DELAY = -1;

  public static void main(String[] args) {

    List<PatientChancePair> patientChancePairs = List.of(
            new PatientChancePair(PatientType.TYPE_1, PATIENT_TYPE_1_CHANCE),
            new PatientChancePair(PatientType.TYPE_2, PATIENT_TYPE_2_CHANCE),
            new PatientChancePair(PatientType.TYPE_3, PATIENT_TYPE_3_CHANCE)
    );

    Create creator = new Create("CREATOR", CREATOR_MEAN_TIME, patientChancePairs);
    Process reception = new ReceptionProcess("RECEPTION", Integer.MAX_VALUE);
    Process wardWalking = new Process("WARD_WALKING", Integer.MAX_VALUE);
    Process laboratoryWalking = new LaboratoryWalkingProcess("LABORATORY_WALKING", Integer.MAX_VALUE);
    Process registry = new Process("REGISTRY", Integer.MAX_VALUE);
    Process laboratory = new LaboratoryProcess("LABORATORY", Integer.MAX_VALUE);
    Exit exit = new Exit("Exit");

    List<Device> dutyDoctors = List.of(
        new DutyDoctor("RECEPTION_DUTY_DOCTOR_1", NO_DELAY, reception),
        new DutyDoctor("RECEPTION_DUTY_DOCTOR_2", NO_DELAY, reception)
    );

    List<Device> accompanyingPersonnel = List.of(
        new Device("ACCOMPANYING_PERSON_1", WARD_WALKING_TIME_MIN, WARD_WALKING_TIME_MAX, wardWalking),
        new Device("ACCOMPANYING_PERSON_2", WARD_WALKING_TIME_MIN, WARD_WALKING_TIME_MAX, wardWalking),
        new Device("ACCOMPANYING_PERSON_3", WARD_WALKING_TIME_MIN, WARD_WALKING_TIME_MAX, wardWalking)
    );

    List<Device> laboratoryWalkingDevices = IntStream.range(0, LAB_WALKING_DEVICE_COUNT).mapToObj(index ->
            new Device("LABORATORY_WALKING_DEVICE_" + (index + 1), LAB_WALKING_TIME_MIN, LAB_WALKING_TIME_MAX, laboratoryWalking)
    ).toList();

    List<Device> registryDoctors = List.of(
        new Device("REGISTRY_DOCTOR_1", REGISTRY_TIME_MEAN, REGISTRY_ERLANG_K, registry)
    );

    List<Device> laboratoryAssistants = List.of(
        new Device("LAB_ASSISTANT_1", LAB_EXAMINATION_TIME_MEAN, LAB_EXAMINATION_ERLANG_K, laboratory),
        new Device("LAB_ASSISTANT_2", LAB_EXAMINATION_TIME_MEAN, LAB_EXAMINATION_ERLANG_K, laboratory)
    );

    reception.setDevices(dutyDoctors);
    wardWalking.setDevices(accompanyingPersonnel);
    laboratoryWalking.setDevices(laboratoryWalkingDevices);
    registry.setDevices(registryDoctors);
    laboratory.setDevices(laboratoryAssistants);

    creator.setNextElements(List.of(
        new ElementChancePair(reception, MAX_CHANCE)
    ));

    reception.setNextElements(List.of(
        new ElementChancePair(wardWalking, NO_CHANCE),
        new ElementChancePair(laboratoryWalking, NO_CHANCE)
    ));

    wardWalking.setNextElements(List.of(
        new ElementChancePair(exit, MAX_CHANCE)
    ));

    laboratoryWalking.setNextElements(List.of(
        new ElementChancePair(registry, NO_CHANCE),
        new ElementChancePair(reception, NO_CHANCE)
    ));

    registry.setNextElements(List.of(
        new ElementChancePair(laboratory, MAX_CHANCE)
    ));

    laboratory.setNextElements(List.of(
        new ElementChancePair(laboratoryWalking, NO_CHANCE),
        new ElementChancePair(exit, NO_CHANCE)
    ));

    List<Element> list = List.of(creator, reception, wardWalking, laboratoryWalking, registry, laboratory, exit);
    Model model = new Model(list);
    model.simulate(IMITATION_TIME);
  }

}
