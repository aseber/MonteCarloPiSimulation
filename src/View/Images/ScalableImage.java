package View.Images;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by aseber on 5/7/16.
 */
public class ScalableImage {

    private BufferedImage image;
    private BufferedImage subImage;

    private double scale;

    private Dimension size;
    private Dimension scaledSize;

    public ScalableImage(Dimension size, int imageType, double scale) {

        this.size = size;
        this.scale = scale;
        scaledSize = new Dimension((int) Math.round(size.getWidth()*scale), (int) Math.round(size.getHeight()*scale));

        image = new BufferedImage(getSize().width, getSize().height, imageType);
        subImage = new BufferedImage(getScaledSize().width, getScaledSize().height, imageType);

    }

    public Image getImage() {

        return scaleSubImageOntoImage();

    }

    private Image scaleSubImageOntoImage() {

        Graphics g = image.getGraphics();

        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, getSize().width, getSize().height);
        g.drawImage(subImage, getStartPoint().x, getStartPoint().y, getScaledSize().width, getScaledSize().height, null);

        return image;

    }

    protected Dimension getSize() {

        return size;

    }

    protected Dimension getScaledSize() {

        return scaledSize;

    }

    private Point getStartPoint() {

        int xStart = (getSize().width - getScaledSize().width) / 2;
        int yStart = (getSize().height - getScaledSize().height) / 2;
        Point scaledStartPoint = new Point(xStart, yStart);

        return scaledStartPoint;

    }

    protected Graphics getGraphics() {

        return subImage.getGraphics();

    }

    protected int getColorAtLocation(Point point) {

        return subImage.getRGB(point.x, point.y);

    }

    protected void setColorAtLocation(Point point, int color) {

        subImage.setRGB(point.x, point.y, color);

    }

    protected Point getScaledMiddle() {

        int x = (int) Math.round(getScaledSize().getWidth() / 2);
        int y = (int) Math.round(getScaledSize().getHeight() / 2);

        return new Point(x, y);

    }

}