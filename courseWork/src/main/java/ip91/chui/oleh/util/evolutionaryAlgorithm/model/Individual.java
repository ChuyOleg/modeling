package ip91.chui.oleh.util.evolutionaryAlgorithm.model;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Individual {

    private final Object[] chromosome;
    private int fitness;

}
