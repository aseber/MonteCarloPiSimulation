package Observables;

import Model.Sample;
import Utilities.Status;

import java.util.Observable;

/**
 * Created by aseber on 5/18/16.
 */
public class StatusObservable extends Observable {

    private Status status;


    public void setStatus(Status status) {

        this.status = status;
        setChanged();

    }

    public void notifyObservers() {

        super.notifyObservers(status);

    }

}
