import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class FractalExplorer {

    // размер экрана
    private int displaySize;

    // Ссылка для обновления отображения в разных методах в процессе вычисления
    private JImageDisplay jDisplay;

    // Ссылка на базовый класс для отображения других видов фракталов в будущем
    private FractalGenerator fractal;

    // диапазон комплексной плоскости, которая выводится на экран
    private Rectangle2D.Double range;

    /**
     * конструктор, который принимает значение размера отображения в качестве
     * аргумента, затем сохраняет это значение в соответствующем поле, а также
     * инициализирует объекты диапазона и фрактального генератора.
     * 
     * @param size - значение размера отображения
     */
    public FractalExplorer(int size) {

        // размер изображения
        displaySize = size;

        // объекты диапазона и фрактального генератора
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        jDisplay = new JImageDisplay(displaySize, displaySize);
    }

    /**
     * инициализирует графический интерфейс и его содержимое
     */
    public void createAndShowGUI() {

        // создание окна (фрейма)
        jDisplay.setLayout(new BorderLayout());
        JFrame lab4Frame = new JFrame("Создание фрактального изображения");

        // вывод созданного изображения в центр
        lab4Frame.add(jDisplay, BorderLayout.CENTER);

        // создание кнопки, которая очищает область и событие сброса изображения
        JButton resetButton = new JButton("Очистить область");
        lab4Frame.add(resetButton, BorderLayout.SOUTH);
        ResetHandler clearHandler = new ResetHandler();
        resetButton.addActionListener(clearHandler);

        // событие нажатия кнопок мыши на изображение
        MouseHandler click = new MouseHandler();
        jDisplay.addMouseListener(click);

        // операция закрытия окна по умолчанию
        lab4Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * Данные операции правильно разметят содержимое окна, сделают его видимым и
         * затем запретят изменение размеров окна
         */
        lab4Frame.pack();
        lab4Frame.setVisible(true);
        lab4Frame.setResizable(false);
    }

    /**
     * Вспомогательный метод для вывода на экран фрактала. Содержит попиксельный
     * цикл.
     */
    private void drawFractal() {

        // цикл для координаты x и вложенный цикл для координаты y
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {

                /**
                 * Поиск соответствующих координат xCoord и yCoord в области отображения
                 * фрактала.
                 */
                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);

                /**
                 * Вычисление количество итераций для соответствующих координат в области
                 * отображения фрактала.
                 */
                int iteration = fractal.numIterations(xCoord, yCoord);

                // Если число итераций равно -1 установить пиксель в черный цвет
                if (iteration == -1)
                    jDisplay.drawPixel(x, y, 0);

                else {

                    // Иначе выбрать значение цвета, основанное на количестве итераций.
                    // example: float hue = 0.7f + (float) iteration / 200f;
                    float hue = 0.7f + (float) iteration / (float) Math.random() / 200f * (float) Math.random();
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    // Обновление изображения в соответствии с цветом для каждого пикселя.
                    jDisplay.drawPixel(x, y, rgbColor);
                }

            }
        }

        // обновление отображения на экране
        jDisplay.repaint();
    }

    private class ResetHandler implements ActionListener {

        /**
         * Обработчик должен сбросить диапазон к начальному, определенному генератором,
         * а затем перерисовать фрактал
         * 
         * @param e - событие
         */
        public void actionPerformed(ActionEvent e) {
            fractal.getInitialRange(range);
            drawFractal();
        }
    }

    //
    private class MouseHandler extends MouseAdapter {

        /**
         * При получении события о щелчке мышью, класс должен отобразить пиксельные
         * кооринаты щелчка в область фрактала, а затем вызвать метод генератора
         * recenterAndZoomRange() с координатами, по которым щелкнули, и масштабом 0.5.
         * 
         * @param e - событие
         */
        @Override
        public void mouseClicked(MouseEvent e) {

            // Получение координаты x при нажатии клавиши мыши
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);

            // Получение координаты y при нажатии клавиши мыши
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);

            // Вызов метода recenterAndZoomRange() с заданными координатами и шкалой 0,5
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            // Перерисовка фрактала
            drawFractal();
        }
    }

    /**
     * Статический метод main() для запуска программы. Инициализируется новый
     * экземпляр класса FractalExplorer с размером отображения 800. Вызов метод
     * createAndShowGUI () класса FractalExplorer. Вызов метод drawFractal() класса
     * FractalExplorer для отображения начального представления.
     * 
     * @param args - аргументы
     */
    public static void main(String[] args) {

        FractalExplorer displayExplorer = new FractalExplorer(800);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}