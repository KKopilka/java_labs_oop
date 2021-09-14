import FractalGenerator;

import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {

    // ограничение на количество повторений
    public static final int max_iteration = 2000;

    /**
     * Метод позволяет генератору фракталов определить наиболее «интересную» область
     * комплексной плоскости для конкретного фрактала Значения x и y будут равны -2
     * и -2.5 соответственно, а ширина и высота будут равны 4.
     * 
     * @param range - прямоугольный предмет
     */
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    /**
     * Реализует итеративную функцию для фрактала Мандельброта.
     * 
     * @param x - координата x
     * @param y - координата y
     * @return - возвращает количество итераций
     */
    public int numIterations(double x, double y) {
        // начальное значение цикла
        int iteration = 0;

        // начальные мнимые и действительные числа
        double z_real = 0;
        double z_imaginary = 0;

        /*
         * Функция для фрактала Burning Ship имеет вид: Zn = (abs[Re(zn-1)] +
         * i(abs[img(zn-1)]))^2, где все значения — это комплексные числа (z_real,
         * z_imaginary), Z0 = 0, и с - определенная точка фрактала, которую мы
         * отображаем на экране. Сравнение будет проводиться до тех пор, пока квадрат
         * числа z по модулю не станет больше 2^2.
         */
        while (iteration < max_iteration && z_real * z_real + z_imaginary * z_imaginary < 4) {
            double z_realUpdated = z_real * z_real - z_imaginary * z_imaginary + x;
            double z_imaginaryUpdated = 2 * Math.abs(z_real) * Math.abs(z_imaginary) + y;
            z_real = z_realUpdated;
            z_imaginary = z_imaginaryUpdated;
            iteration += 1;
        }

        /*
         * Если алгоритм дошел до значения max_iteration нужно вернуть -1, чтобы
         * показать, что точка не выходит за границы.
         */
        if (iteration == max_iteration) {
            return -1;
        }

        // вернуть количество проделанных итераций
        return iteration;
    }

    // Наименование метода для передачи в список
    public String toString() {
        return "Burning Ship";
    }
}