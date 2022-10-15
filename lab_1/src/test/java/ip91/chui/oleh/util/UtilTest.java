package ip91.chui.oleh.util;

import java.util.List;

public class UtilTest {

  public static double processX2(List<Integer> actualFr, List<Integer> expectedFr, int k) {
    double X2 = 0;

    for (int idx = 0; idx < k; idx++) {
      X2 = Math.pow(actualFr.get(idx) - expectedFr.get(idx), 2) / expectedFr.get(idx);
    }

    return X2;
  }

}
