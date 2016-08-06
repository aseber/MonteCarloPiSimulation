package Utilities;

/**
 * Created by aseber on 5/6/16.
 */
public class MathUtilities {

    public static final int clamp(int min, int value, int max) {

        return Math.min(max, Math.max(value, min));

    }

    public static final double distanceSq(double x1, double y1, double x2, double y2) {

        return Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0);

    }

}
