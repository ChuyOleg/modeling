package ip91.chui.oleh;

import ip91.chui.oleh.model.Interval;
import ip91.chui.oleh.service.IntervalInfoUtil;
import ip91.chui.oleh.service.SequenceInfoUtil;
import ip91.chui.oleh.service.generator.ExpGenerator;
import ip91.chui.oleh.util.UtilTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpGeneratorTest {

  private static final double X2_CRITICAL = 28.9;
  private static final int INTERVAL_COUNT = 20;
  private static final int SIZE = 10000;

  private ExpGenerator generator;

  @BeforeEach
  void setUp() {
    generator = new ExpGenerator();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsForExpTesting")
  void shouldSatisfyExpDistributionBasedOnX2Criteria(double lambda) {
    double[] sequence = generator.generate(lambda, SIZE);

    List<Integer> actualFr = getActualFrequencyList(sequence);
    List<Integer> expectedFr = getExpectedFrequencyList(sequence, lambda);

    System.out.println(expectedFr);

    final double X2 = UtilTest.processX2(actualFr, expectedFr, INTERVAL_COUNT);

    System.out.println(X2);

    assertTrue(X2 < X2_CRITICAL);
  }

  private List<Integer> getActualFrequencyList(double[] sequence) {
    double minEl = SequenceInfoUtil.findMinEl(sequence);
    double maxEl = SequenceInfoUtil.findMaxEl(sequence);
    double intervalLength = SequenceInfoUtil.findIntervalLength(minEl, maxEl, INTERVAL_COUNT);

    return IntervalInfoUtil.findFrequencyList(sequence, INTERVAL_COUNT, minEl, maxEl, intervalLength);
  }

  private static List<Integer> getExpectedFrequencyList(double[] sequence, double lambda) {
    double minEl = SequenceInfoUtil.findMinEl(sequence);
    double maxEl = SequenceInfoUtil.findMaxEl(sequence);
    double intervalLength = SequenceInfoUtil.findIntervalLength(minEl, maxEl, INTERVAL_COUNT);
    List<Interval> intervals = IntervalInfoUtil.findIntervals(INTERVAL_COUNT, minEl, intervalLength);
    int[] frequency = new int[INTERVAL_COUNT];

    for (int idx = 0; idx < INTERVAL_COUNT; idx++) {
      Interval interval = intervals.get(idx);
      frequency[idx] = (int) Math.round(SIZE * (Math.exp(-lambda * interval.getStart()) - Math.exp(-lambda * interval.getEnd())));
    }

    return Arrays.stream(frequency).boxed().collect(Collectors.toList());
  }

  private static Stream<Arguments> provideArgumentsForExpTesting() {
    return Stream.of(
        Arguments.of(2),
        Arguments.of(5),
        Arguments.of(10),
        Arguments.of(20),
        Arguments.of(0.5)
    );
  }

}