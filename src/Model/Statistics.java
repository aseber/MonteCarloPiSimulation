package Model;

import Utilities.MathUtilities;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by aseber on 5/9/16.
 */
public class Statistics implements Observer {

    private Observable observable;
    private int currentSampleCount;
    private int samplesInside;
    private int samplesOutside;
    private int maxSampleCount;

    public Statistics(int maxSampleCount, Observable observable) {

        this.maxSampleCount = maxSampleCount;
        this.observable = observable;

    }

    public void update(Observable observable, Object object) {

        if (object instanceof Sample) {

            Sample sample = (Sample) object;
            acceptSample(sample);

        }

    }

    public void acceptSample(Sample sample) {

        if (!canAllocateSample()) {

            throw new InvalidStateException("Statistics: Attempted to add more samples than initially allocated for");

        }

        allocateSample(sample);

    }

    private boolean canAllocateSample() {

        return currentSampleCount < maxSampleCount;

    }

    private void allocateSample(Sample sample) {

        if (isSampleInsideCircle(sample)) {

            incrementInsideSampleCount();

        } else {

            incrementOutsideSampleCount();

        }

        incrementSampleCount();
        notifyObservers();

    }

    private void notifyObservers() {

        getObservable().notifyObservers(this);

    }

    private Observable getObservable() {

        return observable;

    }

    private void incrementInsideSampleCount() {

        samplesInside++;

    }

    private void incrementOutsideSampleCount() {

        samplesOutside++;

    }

    private void incrementSampleCount() {

        currentSampleCount++;

    }

    private boolean isSampleInsideCircle(Sample sample) {

        return MathUtilities.distanceSq(sample.getXLocation(), sample.getYLocation(), 0.0, 0.0) <= 1.0;

    }

    public int getCurrentSampleCount() {

        return currentSampleCount;

    }

    public int getSamplesInside() {

        return samplesInside;

    }

    public int getMaxSampleCount() {

        return maxSampleCount;

    }

    public int getSamplesOutside() {

        return samplesOutside;

    }

    public double getPercentSamplesAllocated() {

        return ((double) getCurrentSampleCount()) / ((double) getMaxSampleCount());

    }

    public double getPercentageInside() {

        return ((double) getSamplesInside()) / ((double) getCurrentSampleCount());

    }

    public double getApproximation() {

        // s = 2; r = 1

        // Area of square = s^2
        // Area of circle = pi*r^2

        // Association: Area of circle = [0.0, 1.0] * Area of square

        // So, [0.0. 1.0] * s^2 = pi*r^2

        // And, pi = ([0.0, 1.0] * s^2)/(r^2)

        return (getPercentageInside() * 4.0);

    }

    public double getPercentageApproximationAccuracy() {

        return 1.0 - getApproximation() / Math.PI;

    }

}
