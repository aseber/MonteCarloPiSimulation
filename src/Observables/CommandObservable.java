package Observables;

import Utilities.Command;

import java.util.Observable;

/**
 * Created by aseber on 5/18/16.
 */
public class CommandObservable<T> extends Observable {

    public void notifyObservers(Command<T> command) {

        setChanged();
        super.notifyObservers(command);


    }

}
