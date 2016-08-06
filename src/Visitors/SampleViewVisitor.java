package Visitors;

import View.Images.SampleForeground;
import Model.Sample;

/**
 * Created by aseber on 5/6/16.
 */
public class SampleViewVisitor {

    private SampleForeground sampleArea;

    public SampleViewVisitor(SampleForeground sampleArea) {

        this.sampleArea = sampleArea;

    }

    public void visit(Sample sample) {

        sampleArea.addSample(sample);

    }

    public SampleForeground getSampleForeground() {

        return sampleArea;

    }

}
