package ip91.chui.oleh;

import ip91.chui.oleh.imitation.ModelBuilder;
import ip91.chui.oleh.imitation.service.Model;

import static ip91.chui.oleh.imitation.config.Config.*;

public class SimModel {

  public static void main(String[] args) {
    final Model model = ModelBuilder.build(GREEN_1_BREAK, GREEN_2_BREAK);
    model.simulate(IMITATION_TIME);
  }

}
