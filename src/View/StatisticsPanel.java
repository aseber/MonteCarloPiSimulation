package View;

import Model.Statistics;
import Model.StatisticsUpdateInterface;
import View.Images.SampleResultsForeground;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by aseber on 5/22/16.
 */
public class StatisticsPanel extends JPanel implements Observer, StatisticsUpdateInterface {

    private SampleResultsForeground foreground;

    public StatisticsPanel(Dimension dimension) {

        foreground = new SampleResultsForeground(dimension, 1.0);

    }

    public void updateStatisitcs(Statistics statistics) {

        foreground.updateStatisticsView(statistics);

    }

    public void paintComponent(Graphics g) {

        g.drawImage(foreground.getImage(), 0, 0, null);

    }

    public void update(Observable observable, Object object) {

        Statistics statistics = (Statistics) object;

    }

}
