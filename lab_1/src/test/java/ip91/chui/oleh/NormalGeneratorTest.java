package ip91.chui.oleh;

import ip91.chui.oleh.model.Interval;
import ip91.chui.oleh.service.IntervalInfoUtil;
import ip91.chui.oleh.service.SequenceInfoUtil;
import ip91.chui.oleh.service.generator.NormalGenerator;
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

class NormalGeneratorTest {

  private static final double X2_CRITICAL = 27.6;
  private static final int INTERVAL_COUNT = 20;
  private static final int SIZE = 10000;

  private NormalGenerator generator;

  @BeforeEach
  void setUp() {
    generator = new NormalGenerator();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsForExpTesting")
  void shouldSatisfyUniformDistributionBasedOnX2Criteria(double alpha, double sigma) {
    double[] sequence = generator.generate(alpha, sigma, SIZE);

    List<Integer> actualFr = getActualFrequencyList(sequence);
    List<Integer> expectedFr = getExpectedFrequencyList(sequence, alpha, sigma);

    final double X2 = UtilTest.processX2(actualFr, expectedFr, INTERVAL_COUNT);

    assertTrue(X2 < X2_CRITICAL);
  }

  private List<Integer> getActualFrequencyList(double[] sequence) {
    double minEl = SequenceInfoUtil.findMinEl(sequence);
    double maxEl = SequenceInfoUtil.findMaxEl(sequence);
    double intervalLength = SequenceInfoUtil.findIntervalLength(minEl, maxEl, INTERVAL_COUNT);

    return IntervalInfoUtil.findFrequencyList(sequence, INTERVAL_COUNT, minEl, maxEl, intervalLength);
  }

  private List<Integer> getExpectedFrequencyList(double[] sequence, double alpha, double sigma) {
    double minEl = SequenceInfoUtil.findMinEl(sequence);
    double maxEl = SequenceInfoUtil.findMaxEl(sequence);
    double intervalLength = SequenceInfoUtil.findIntervalLength(minEl, maxEl, INTERVAL_COUNT);
    List<Interval> intervals = IntervalInfoUtil.findIntervals(INTERVAL_COUNT, minEl, intervalLength);
    int[] frequency = new int[INTERVAL_COUNT];

    for (int idx = 0; idx < INTERVAL_COUNT; idx++) {
      Interval interval = intervals.get(idx);
      double x = (interval.getStart() + interval.getEnd()) / 2;
      double funcVal = (1 / (sigma * Math.sqrt(2 * Math.PI))) * Math.exp(-Math.pow(x - alpha, 2) / (2 * Math.pow(sigma, 2)));
      double integralVal = (interval.getEnd() - interval.getStart()) * (funcVal);
      frequency[idx] = (int) Math.round(SIZE * integralVal);
    }

    return Arrays.stream(frequency).boxed().collect(Collectors.toList());
  }

  private static Stream<Arguments> provideArgumentsForExpTesting() {
    return Stream.of(
        Arguments.of(3, 4),
        Arguments.of(8, 8),
        Arguments.of(2, 1),
        Arguments.of(100, 4),
        Arguments.of(4, 100)
    );
  }

}