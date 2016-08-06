package Observables;

import Model.Sample;

import java.util.Observable;

/**
 * Created by aseber on 5/15/16.
 */
public class SampleObservable extends Observable {

    private Sample sample;

    public void setSample(Sample sample) {

        this.sample = sample;
        setChanged();

    }

    public void notifyObservers() {

        super.notifyObservers(sample);


    }

}
