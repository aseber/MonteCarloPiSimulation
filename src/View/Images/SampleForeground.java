package View.Images;

import Model.Sample;
import Model.SampleAdditionInterface;
import Utilities.MathUtilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by aseber on 5/6/16.
 */
public class SampleForeground extends ScalableImage implements Observer, SampleAdditionInterface {

    public SampleForeground(Dimension size, double scale) {

        super(size, BufferedImage.TYPE_4BYTE_ABGR, scale);

    }

    public void update(Observable observable, Object object) {

        Sample sample = (Sample) object;
        addSample(sample);

    }

    // Expected values
    //      xLocation: [-1, 1]
    //      yLocation: [-1, 1]
    public void addSample(Sample sample) {

        double xLocation = sample.getXLocation();
        double yLocation = sample.getYLocation();

        // Invert the x component
        yLocation = -yLocation;

        Point beginning = new Point(0, 0);
        Point middle = getScaledMiddle();
        Point end = new Point(getScaledSize().width - 1, getScaledSize().height - 1);

        int xImageLocation = MathUtilities.clamp(beginning.x, (int) Math.round(xLocation*middle.getX()) + middle.x, end.x);
        int yImageLocation = MathUtilities.clamp(beginning.y, (int) Math.round(yLocation*middle.getY()) + middle.y, end.y);
        Point sampleScreenLocation = new Point(xImageLocation, yImageLocation);

        Color color = new Color(getColorAtLocation(sampleScreenLocation));
        color = increaseSampleColor(color);

        setColorAtLocation(sampleScreenLocation, color.getRGB());

    }

    private Color increaseSampleColor(Color color) {

        int red = MathUtilities.clamp(0, color.getRed() + 50, 255);
        int green = MathUtilities.clamp(0, color.getGreen() + 50, 255);
        int blue = MathUtilities.clamp(0, color.getBlue() + 50, 255);

        Color output = new Color(red, green, blue);

        return output;

    }

}
