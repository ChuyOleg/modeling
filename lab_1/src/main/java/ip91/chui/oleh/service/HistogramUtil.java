package ip91.chui.oleh.service;

import ip91.chui.oleh.model.HistogramData;
import ip91.chui.oleh.model.Interval;

import java.util.List;
import java.util.stream.Collectors;

public class HistogramUtil {

  private static final String X_AXIS_STRING_FORMAT = "%.2f";

  public static HistogramData prepareHistogramData(List<Interval> intervals, List<Integer> frequency) {
   List<String> histogramIntervals = intervals.stream()
       .map(interval -> String.format(X_AXIS_STRING_FORMAT, interval.getStart()))
       .collect(Collectors.toList());

   return new HistogramData(histogramIntervals, frequency);
  }

}
