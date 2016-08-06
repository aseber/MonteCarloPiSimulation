package View;

import Model.Sample;
import Model.SampleAdditionInterface;
import Model.Statistics;
import Model.StatisticsUpdateInterface;
import Utilities.Status;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by aseber on 5/5/16.
 */
public class View implements Observer, Runnable, SampleAdditionInterface {

    private final int FPS = 30;
    private int id;
    private JFrame frame;
    private String frameTitle = "PI Simulation";
    private JPanel viewingArea;
    private SampleAdditionInterface drawingPanel;
    private StatisticsUpdateInterface statisticsPanel;
    private LinkedBlockingQueue<Sample> samplesToAdd = new LinkedBlockingQueue<>();
    private Timer frameTimer;

    public View(int id) {

        this.id = id;

    }

    public void run() {

        createView();
        setupDrawingTimer();

        while(true) {

            addSample();

        }

    }

    private void setupDrawingTimer() {

        frameTimer = new Timer(getTimeBetweenFrames(), (e) -> update());
        frameTimer.start();

    }

    private int getTimeBetweenFrames() {

        return 1000 / getFPS();

    }

    private int getFPS() {

        return FPS;

    }

    private void updateFrameTitle(Status status) {

        // This doesn't work if the frame is null. Should use the command thing I built but it requires
        // bad dependencies on the simulation class
        System.out.println(status + " | " + frame);

        if (frame != null) {

            frame.setTitle(frameTitle + " #" + getId() + " - " + status);

        }

    }

    public int getId() {

        return id;

    }

    private void addSample() {

        if (!samplesToAdd.isEmpty()) {

            Sample sample = samplesToAdd.poll();
            drawingPanel.addSample(sample);

        }

    }

    public void addSample(Sample sample) {

        samplesToAdd.add(sample);

    }

    public void updateStatistics(Statistics statistics) {

        statisticsPanel.updateStatisitcs(statistics);

    }

    public void update(Observable observable, Object object) {

        if (object instanceof Sample) {

            Sample sample = (Sample) object;
            addSample(sample);

        }

        if (object instanceof Statistics) {

            Statistics statistics = (Statistics) object;
            updateStatistics(statistics);

        }

        else if (object instanceof Status) {

            Status status = (Status) object;
            updateFrameTitle(status);

        }

        else if (object instanceof Statistics) {

            Statistics statistics = (Statistics) object;
            updateStatistics(statistics);

        }

    }

    public void update() {

        viewingArea.repaint();

    }

    private void createView() {

        initialize();
        createViewingArea();
        finalizeFrame();

    }

    private void initialize() {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(500, 550));

    }

    private void createViewingArea() {

        viewingArea = new JPanel();
        viewingArea.setBackground(Color.WHITE);
        viewingArea.setPreferredSize(frame.getSize());
        BoxLayout boxLayout = new BoxLayout(viewingArea, BoxLayout.Y_AXIS);
        viewingArea.setLayout(boxLayout);

        createDrawingArea();
        createApproximationArea();

    }

    private void createDrawingArea() {

        DrawingPanel drawingArea = new DrawingPanel(new Dimension(500, 500));
        drawingArea.setPreferredSize(new Dimension(500, 500));
        drawingArea.setBackground(Color.BLACK);

        viewingArea.add(drawingArea);

        drawingPanel = drawingArea;

    }

    private void createApproximationArea() {

        StatisticsPanel approximationArea = new StatisticsPanel(new Dimension(500, 50));
        approximationArea.setPreferredSize(new Dimension(500, 50));
        approximationArea.setBackground(Color.RED);

        viewingArea.add(approximationArea);

        statisticsPanel = approximationArea;

    }

    private void finalizeFrame() {

        frame.add(viewingArea);
        frame.pack();
        frame.setVisible(true);

    }

}
