package ip91.chui.oleh.service.generator;

import java.util.stream.IntStream;

public class ExpGenerator {

  public double[] generate(double lambda, int size) {
    return IntStream.range(0, size)
        .mapToDouble((idx) -> generateSingleNum(lambda))
        .toArray();
  }

  private double generateSingleNum(double lambda) {
    return -1 / lambda * Math.log(Math.random());
  }

}
