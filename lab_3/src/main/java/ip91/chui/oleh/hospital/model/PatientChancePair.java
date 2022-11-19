package ip91.chui.oleh.hospital.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PatientChancePair {

    private final PatientType patient;
    private final double chance;

}
