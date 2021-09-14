import java.awt.geom.*;

public class BurningShip extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double rectangle) {
        // rectangle.x = -2;
        // rectangle.y = -1.5;
        // rectangle.width = 3;
        // rectangle.height = 3;
        rectangle.setFrame(-2, -2.5, 4, 4);
    }

    public int numIterations(double x, double y) {
        double realZ = 0.0;
        double imageZ = 0.0;
        int i = 0;
        while (realZ * realZ + imageZ * imageZ < 4 && i < MAX_ITERATIONS) {
            double newRealZ = realZ * realZ - imageZ * imageZ + x;
            double newImageZ = 2 * Math.abs(realZ) * Math.abs(imageZ) + y;
            realZ = newRealZ;
            imageZ = newImageZ;
            i++;

        }
        if (i == MAX_ITERATIONS) {
            return -1;
        } else
            return i;
    }

    public String toString() {
        return "BurningShip";
    }
}