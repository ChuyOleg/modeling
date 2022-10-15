package ip91.chui.oleh;

import ip91.chui.oleh.model.GenType;
import ip91.chui.oleh.model.HistogramData;
import ip91.chui.oleh.model.Interval;
import ip91.chui.oleh.service.Histogram;
import ip91.chui.oleh.service.HistogramUtil;
import ip91.chui.oleh.service.IntervalInfoUtil;
import ip91.chui.oleh.service.SequenceInfoUtil;
import ip91.chui.oleh.service.generator.ExpGenerator;
import ip91.chui.oleh.service.generator.NormalGenerator;
import ip91.chui.oleh.service.generator.UniformGenerator;

import java.util.List;

import static ip91.chui.oleh.config.Config.*;

public class App {

  private final Histogram histogram;

  public App() {
    histogram = new Histogram();
  }

  public void run(GenType genType) {
    double[] sequence = generate(genType);

    System.out.println("Average num: " + SequenceInfoUtil.findAverageNumber(sequence));
    System.out.println("Dispersion: " + SequenceInfoUtil.findDispersion(sequence));

    HistogramData histogramData = prepareData(sequence);

    histogram.draw(histogramData);
  }

  private double[] generate(GenType genType) {
    return switch (genType) {
      case EXP -> new ExpGenerator().generate(EXP_LAMBDA, SEQ_SIZE);
      case NORMAL -> new NormalGenerator().generate(NORMAL_ALPHA, NORMAL_SIGMA, SEQ_SIZE);
      case UNIFORM -> new UniformGenerator().generate(UNIFORM_A, UNIFORM_C, SEQ_SIZE);
    };
  }

  private HistogramData prepareData(double[] sequence) {
    final double minEl = SequenceInfoUtil.findMinEl(sequence);
    final double maxEl = SequenceInfoUtil.findMaxEl(sequence);
    final double intervalLength = SequenceInfoUtil.findIntervalLength(minEl, maxEl, INTERVAL_COUNT);

    List<Interval> intervals = IntervalInfoUtil.findIntervals(INTERVAL_COUNT, minEl, intervalLength);
    List<Integer> frequency = IntervalInfoUtil.findFrequencyList(sequence, INTERVAL_COUNT, minEl, maxEl, intervalLength);

    return HistogramUtil.prepareHistogramData(intervals, frequency);
  }

}
