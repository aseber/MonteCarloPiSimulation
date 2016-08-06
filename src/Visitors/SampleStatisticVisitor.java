package Visitors;

import Model.Sample;
import Model.Statistics;

/**
 * Created by aseber on 5/9/16.
 */
public class SampleStatisticVisitor {

    private Statistics statistics;

    public SampleStatisticVisitor(Statistics statistics) {

        this.statistics = statistics;

    }

    public void visit(Sample sample) {

        statistics.acceptSample(sample);

    }

    public Statistics getStatistics() {

        return statistics;

    }

}
