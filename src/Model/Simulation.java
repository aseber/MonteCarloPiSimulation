package Model;

import Observables.SampleObservable;
import Observables.StatusObservable;
import Utilities.Status;

/**
 * Created by aseber on 5/5/16.
 */
public class Simulation implements Runnable {

    private int SPS = 5000; // Samples Per Second
    private long currentTime;
    private Status status;
    SampleGenerator sampleGenerator;
    int currentSampleCount;
    int maxSampleCount;

    SampleObservable sampleObservable;
    StatusObservable statusObservable;

    public Simulation(int maxSampleCount, SampleObservable sampleObservable, StatusObservable statusObservable) {

        this.maxSampleCount = maxSampleCount;
        sampleGenerator = new SampleGenerator();
        this.sampleObservable = sampleObservable;
        this.statusObservable = statusObservable;

        setStatus(Status.WAITING);

    }

    public void start() {

        setStatus(Status.RUNNING);

    }

    private void setStatus(Status status) {

        this.status = status;
        getStatusObservable().setStatus(status);
        getStatusObservable().notifyObservers();

    }

    public Status getStatus() {

        return status;

    }

    private void finishSimulation() {

        setStatus(Status.FINISHED);

    }

    private void setCurrentTime() {

        currentTime = System.currentTimeMillis();

    }

    private long getCurrentTime() {

        return currentTime;

    }

    private boolean shouldSelectSamples() {

        return System.currentTimeMillis() > (getCurrentTime() + 1000);

    }

    public void run() {

        while (!isFinished()) {

            while (isRunning()) {

                while (!maxSamplesReached()) {

                    while (shouldSelectSamples()) {

                        selectNextSamples();

                    }

                }

                finishSimulation();

            }

        }

    }

    private void selectNextSamples() {

        setCurrentTime();

        for (int i = 0; i < getDigestSize(); i++) {

            findNextSample();

        }

    }

    private int getDigestSize() {

        // If we add the SPS to the current value and we're still under the max, give the SPS
        if (getSampleCount() + getSamplesPerSecond() < getMaxSampleCount()) {

            return getSamplesPerSecond();

        }

        // Else give the remainder until maxing
        return getMaxSampleCount() - getSampleCount();

    }

    private int getSamplesPerSecond() {

        return SPS;

    }

    private StatusObservable getStatusObservable() {

        return statusObservable;

    }

    private SampleObservable getSampleObservable() {

        return sampleObservable;

    }

    private boolean isRunning() {

        return getStatus() == Status.RUNNING;

    }

    private boolean isFinished() {

        return getStatus() == Status.FINISHED;

    }

    private Sample findNextSample() {

        incrementSampleCount();
        Sample sample = generateSample();

        getSampleObservable().setSample(sample);
        notifySampleObservers();

        return sample;

    }

    private void notifySampleObservers() {

        getSampleObservable().notifyObservers();

    }

    private void notifyStatusObservers() {

        getStatusObservable().notifyObservers();

    }

    private Sample generateSample() {

        return sampleGenerator.getNextSample();

    }

    private void incrementSampleCount() {

        currentSampleCount++;

    }

    private int getSampleCount() {

        return currentSampleCount;

    }

    private int getMaxSampleCount() {

        return maxSampleCount;

    }

    private boolean maxSamplesReached() {

        return getSampleCount() >= getMaxSampleCount();

    }

    public boolean isSimulationRunning() {

        return !maxSamplesReached();

    }

}
