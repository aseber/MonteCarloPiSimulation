package Builders;

import Model.Simulation;
import Model.Statistics;
import Observables.SampleObservable;
import Observables.StatusObservable;
import View.View;

import java.util.Observable;

/**
 * Created by aseber on 5/15/16.
 */
public class SimulationBuilder {

    private int currentIndex = 1;

    public Simulation CreateSimulation(int sampleCount) {

        // Create the view
        View view = new View(getCurrentIndex());
        Thread viewThread = new Thread(view);
        viewThread.setName("View thread #" + getCurrentIndex());
        viewThread.start();

        // Add the view to the statistics observable
        Observable statisticsObservable = new Observable();
        statisticsObservable.addObserver(view);

        // Then create the statistics
        Statistics statistics = new Statistics(sampleCount, statisticsObservable);

        // Then create the simulation observables
        SampleObservable sampleObservable = new SampleObservable();
        sampleObservable.addObserver(statistics);
        sampleObservable.addObserver(view);
        //
        StatusObservable statusObservable = new StatusObservable();
        statusObservable.addObserver(statistics);
        statusObservable.addObserver(view);

        // And create the simulation
        Simulation simulation = new Simulation(sampleCount, sampleObservable, statusObservable);
        Thread simulationThread = new Thread(simulation);
        simulationThread.setName("Simulation thread #" + getCurrentIndex());
        simulationThread.start();

        incrementCurrentIndex();

        return simulation;

    }

    public int getCurrentIndex() {

        return currentIndex;

    }

    private void incrementCurrentIndex() {

        currentIndex++;

    }

}