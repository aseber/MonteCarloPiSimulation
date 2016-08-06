package View;

import Model.SampleAdditionInterface;
import Model.Simulation;
import View.Images.ImageBackground;
import View.Images.SampleForeground;
import Model.Sample;
import Visitors.SampleViewVisitor;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by aseber on 5/5/16.
 */
public class DrawingPanel extends JPanel implements Observer, SampleAdditionInterface {

    private Dimension size;
    private ImageBackground background;
    private SampleForeground sampleForeground;

    public DrawingPanel(Dimension size) {

        this.size = size;
        background = new ImageBackground(size, 0.8);
        sampleForeground = new SampleForeground(size, 0.8);

    }

    public void addSample(Sample sample) {

        sampleForeground.addSample(sample);

    }

    public void update(Observable observable, Object object) {

        SampleViewVisitor visitor = new SampleViewVisitor(sampleForeground);
        Sample sample = (Sample) object;
        visitor.visit(sample);
        sampleForeground = visitor.getSampleForeground();

    }

    public void paintComponent(Graphics g) {

        ui.update(g, this);
        g.drawImage(sampleForeground.getImage(), 0, 0, null);
        g.drawImage(background.getImage(), 0, 0, null);

    }

}