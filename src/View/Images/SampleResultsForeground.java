package View.Images;

import Model.Statistics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * Created by aseber on 5/9/16.
 */
public class SampleResultsForeground extends ScalableImage {

    public SampleResultsForeground(Dimension size, double scale) {

        super(size, BufferedImage.TYPE_3BYTE_BGR, scale);

    }

    public void update(Observable observable, Object object) {

        Statistics statistics = (Statistics) object;
        updateStatisticsView(statistics);

    }

    public void updateStatisticsView(Statistics statistics) {

        System.out.println("BAHH");
        Graphics g = getImage().getGraphics();
        g.drawString("Hello: " + statistics.getApproximation(), 0, 0);

    }

}
