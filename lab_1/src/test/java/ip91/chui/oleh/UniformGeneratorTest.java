package ip91.chui.oleh;

import ip91.chui.oleh.service.IntervalInfoUtil;
import ip91.chui.oleh.service.SequenceInfoUtil;
import ip91.chui.oleh.service.generator.UniformGenerator;
import ip91.chui.oleh.util.UtilTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UniformGeneratorTest {

  private static final double X2_CRITICAL = 27.6;
  private static final int INTERVAL_COUNT = 20;
  private static final int SIZE = 10000;

  private UniformGenerator generator;

  @BeforeEach
  void setUp() {
    generator = new UniformGenerator();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsForExpTesting")
  void shouldSatisfyUniformDistributionBasedOnX2Criteria(double a, double c) {
    double[] sequence = generator.generate(a, c, SIZE);

    List<Integer> actualFr = getActualFrequencyList(sequence);
    List<Integer> expectedFr = getExpectedFrequencyList();

    final double X2 = UtilTest.processX2(actualFr, expectedFr, INTERVAL_COUNT);

    assertTrue(X2 < X2_CRITICAL);
  }

  private List<Integer> getActualFrequencyList(double[] sequence) {
    double minEl = SequenceInfoUtil.findMinEl(sequence);
    double maxEl = SequenceInfoUtil.findMaxEl(sequence);
    double intervalLength = SequenceInfoUtil.findIntervalLength(minEl, maxEl, INTERVAL_COUNT);

    return IntervalInfoUtil.findFrequencyList(sequence, INTERVAL_COUNT, minEl, maxEl, intervalLength);
  }

  private List<Integer> getExpectedFrequencyList() {
    int[] frequency = new int[INTERVAL_COUNT];

    for (int idx = 0; idx < INTERVAL_COUNT; idx++) {
      frequency[idx] = SIZE / INTERVAL_COUNT;
    }

    return Arrays.stream(frequency).boxed().collect(Collectors.toList());
  }

  private static Stream<Arguments> provideArgumentsForExpTesting() {
    return Stream.of(
        Arguments.of(Math.pow(5, 13), Math.pow(2, 31)),
        Arguments.of(Math.pow(5, 11), Math.pow(2, 30)),
        Arguments.of(Math.pow(5, 10), Math.pow(2, 29)),
        Arguments.of(Math.pow(7, 8), Math.pow(2, 25)),
        Arguments.of(Math.pow(2, 10), Math.pow(2, 29))
    );
  }

}
