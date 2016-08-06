import Builders.SimulationBuilder;
import Model.Simulation;
import View.View;

/**
 * Created by aseber on 5/5/16.
 */
public class Main {

    public static void main(String[] args) {

        Thread.currentThread().setName("Main Thread");

        SimulationBuilder builder = new SimulationBuilder();

        Simulation simulation2 = builder.CreateSimulation(100000);
        simulation2.start();

    }

}
