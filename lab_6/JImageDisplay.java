import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

class JImageDisplay extends JComponent {
    private BufferedImage myImage;

    public BufferedImage getImage() {
        return myImage;
    }

    public JImageDisplay(int height, int width) {
        myImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Dimension newDimension = new Dimension(width, height);
        super.setPreferredSize(newDimension);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(myImage, 0, 0, myImage.getWidth(), myImage.getHeight(), null);
    }

    public void clearImage() {
        int[] rgbArray = new int[myImage.getWidth() * myImage.getHeight()];
        myImage.setRGB(0, 0, myImage.getWidth(), myImage.getHeight(), rgbArray, 0, 1);
    }

    public void drawPixel(int x, int y, int rgbColor) {
        myImage.setRGB(x, y, rgbColor);
    }
}