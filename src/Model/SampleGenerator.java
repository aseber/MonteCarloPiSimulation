package Model;

import java.util.Random;

/**
 * Created by aseber on 5/9/16.
 */
public class SampleGenerator {

    private Random rng;

    public SampleGenerator() {

        rng = new Random();

    }

    public Sample getNextSample() {

        return new Sample(nextDouble(), nextDouble());

    }

    private double nextDouble() {

        return (rng.nextDouble() * 2) - 1;

    }

}
