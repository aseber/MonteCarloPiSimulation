package View.Images;

import Utilities.MathUtilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

/**
 * Created by aseber on 5/7/16.
 */
public class ImageBackground extends ScalableImage {

    public ImageBackground(Dimension size, double scale) {

        super(size, BufferedImage.TYPE_4BYTE_ABGR, scale);

        //Get the graphics and draw out the custom stuff
        Graphics g = getGraphics();
        drawGraph(g);
        drawCircle(g);

    }

    private void drawGraph(Graphics g) {

        g.setColor(Color.WHITE);
        drawLines(g);
        drawNumbers(g);

    }

    private void drawLines(Graphics g) {

        Point middlePoint = getScaledMiddle();

        g.drawLine(middlePoint.x, 0, middlePoint.x, getScaledSize().height);
        g.drawLine(0, middlePoint.y, getScaledSize().width, middlePoint.y);

    }

    private void drawNumbers(Graphics g) {

        DecimalFormat formatter = new DecimalFormat("#.###");
        Point middlePoint = getScaledMiddle();

        double max = 1.0;
        double interval = 0.25;

        for (int deg = 0; deg < 360; deg += 90) {

            for (int i = 1; i <= (int) (max / interval); i++) {

                double rad = Math.toRadians(deg);
                double xAngleModifier = Math.cos(rad);
                double yAngleModifier = Math.sin(rad);

                // Calculate the initial x and y positions
                int xPosition = MathUtilities.clamp(0, (int) Math.round(xAngleModifier*i*interval*middlePoint.getX() + middlePoint.getX()), getScaledSize().width - 1);
                int yPosition = MathUtilities.clamp(0, (int) Math.round(yAngleModifier*i*interval*middlePoint.getY() + middlePoint.getY()), getScaledSize().height - 1);

                double value = interval * i;
                String string = formatter.format(value);

                // Subtract out the string's half size from x and y
                int stringHalfWidth = g.getFontMetrics().stringWidth(string) / 2;
                int stringHalfHeight = g.getFontMetrics().getHeight() / 3;
                xPosition -= stringHalfWidth;
                yPosition += stringHalfHeight;

                // Add in an offset to the string to the direction perpendicular to the angle equal to half*1.2 the x size
                rad = Math.toRadians(deg - 90);
                xAngleModifier = Math.cos(rad);
                yAngleModifier = Math.sin(rad);
                xPosition = MathUtilities.clamp(0, xPosition + Math.abs((int) Math.round(xAngleModifier*stringHalfWidth*1.5)), 499);
                yPosition = MathUtilities.clamp(0, yPosition - Math.abs((int) Math.round(yAngleModifier*stringHalfHeight*2.0)), 499);

                g.drawString(string, xPosition, yPosition);

            }

        }

    }

    private void drawCircle(Graphics g) {

        g.drawArc(0, 0, getScaledSize().width, getScaledSize().height, 0, 360);

    }

}
