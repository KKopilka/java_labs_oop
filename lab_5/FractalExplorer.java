import FractalMethod.BurningShip;
import FractalMethod.Mandelbrot;
import FractalMethod.Tricorn;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FractalExplorer {

    private int rowsRemaining;

    // размер экрана
    private final int displaySize;

    // Ссылка для обновления отображения в разных методах в процессе вычисления
    private final JImageDisplay jDisplay;

    // Ссылка на базовый класс для отображения других видов фракталов в будущем
    private FractalGenerator fractal;

    // диапазон комплексной плоскости, которая выводится на экран
    private final Rectangle2D.Double range;

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
        JFrame lab5Frame = new JFrame("Создание фрактального изображения");

        // вывод созданного изображения в центр
        lab5Frame.add(jDisplay, BorderLayout.CENTER);

        // событие нажатия кнопок мыши на изображение
        MouseHandler click = new MouseHandler();
        jDisplay.addMouseListener(click);

        // операция закрытия окна по умолчанию
        lab5Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // создание экземпляра выпадающего списка (combo-box) и наполнение его данными
        // из фрактальных методов
        JComboBox<FractalGenerator> methodComboBox = new JComboBox<>();
        FractalGenerator mandelbrotMethod = new Mandelbrot();
        methodComboBox.addItem(mandelbrotMethod);
        FractalGenerator tricornMethod = new Tricorn();
        methodComboBox.addItem(tricornMethod);
        FractalGenerator burningShipMethod = new BurningShip();
        methodComboBox.addItem(burningShipMethod);

        // событие выпадающего списка
        ButtonHandler fractalChooser = new ButtonHandler();
        methodComboBox.addActionListener(fractalChooser);

        // создание верхней панели для вывода выпадающего списка
        JPanel upPanel = new JPanel();
        JLabel methodLabel = new JLabel("Метод фрактальных рисунков: ");
        upPanel.add(methodLabel);
        upPanel.add(methodComboBox);
        lab5Frame.add(upPanel, BorderLayout.NORTH);

        // Создание нижней панели.
        JPanel buttonPanel = new JPanel();
        lab5Frame.add(buttonPanel, BorderLayout.SOUTH);

        // создание кнопки, которая очищает область и событие сброса изображения
        JButton resetButton = new JButton("Сбросить");
        buttonPanel.add(resetButton);
        ButtonHandler clearHandler = new ButtonHandler();
        resetButton.addActionListener(clearHandler);

        // Создание кнопки сохранения фрактально рисунка и событие, которые происходит
        // при нажатии на кнопку сохранить
        JButton saveButton = new JButton("Сохранить");
        buttonPanel.add(saveButton);
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);

        /*
         * Данные операции правильно разметят содержимое окна, сделают его видимым и
         * затем запретят изменение размеров окна
         */
        lab5Frame.pack();
        lab5Frame.setVisible(true);
        lab5Frame.setResizable(false);
    }

    /**
     * Вспомогательный метод для вывода на экран фрактала. Содержит попиксельный
     * цикл.
     */
    private void drawFractal() {

        // цикл для координаты x и вложенный цикл для координаты y
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {

                /*
                 * Поиск соответствующих координат xCoord и yCoord в области отображения
                 * фрактала.
                 */
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
                rowsRemaining = displaySize;
                /*
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

    // включение - отключение gui
    public void enableGUI(boolean b) {
        buttonSave.setEnabled(b);
        buttonReset.setEnabled(b);
        comboBox.setEnabled(b);
    }

    // обработчик события нажатия кнопки в приложении
    private class ButtonHandler implements ActionListener {

        /**
         * Обработчик должен сбросить диапазон к начальному, определенному генератором,
         * а затем перерисовать фрактал
         * 
         * @param e - событие
         */

        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            if (e.getSource() instanceof JComboBox) {
                JComboBox<FractalGenerator> mySource = (JComboBox) e.getSource();
                fractal = (FractalGenerator) mySource.getSelectedItem();
                assert fractal != null;
                fractal.getInitialRange(range);
                drawFractal();

            }

            // выполнить событие, если нажата кнопка "Сбросить"
            else if (command.equals("Сбросить")) {
                fractal.getInitialRange(range);
                drawFractal();
            }

            // выполнить событие, если нажата кнопка сохранить
            else if (command.equals("Сохранить")) {

                // создание экземляра файла и последующие действия с ним, а также назначение
                // формата файла по умолчанию
                JFileChooser myFileChooser = new JFileChooser();
                FileFilter extensionFilter = new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(extensionFilter);
                myFileChooser.setAcceptAllFileFilterUsed(false);

                int userSelection = myFileChooser.showSaveDialog(jDisplay);

                if (userSelection == JFileChooser.APPROVE_OPTION) {

                    java.io.File file = myFileChooser.getSelectedFile();

                    // сделать попытку сохранить файл или выдать исключение
                    try {
                        BufferedImage displayImage = jDisplay.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(jDisplay, exception.getMessage(),
                                "Невозможно сохранить изображение", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

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
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);

            // Получение координаты y при нажатии клавиши мыши
            int y = e.getY();
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

            // Вызов метода recenterAndZoomRange() с заданными координатами и шкалой 0,5
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            // Перерисовка фрактала
            drawFractal();
        }
    }

    public class FractalWorker extends SwingWorker<Object, Object> {
        private int y_coord;
        private int[] rgb;

        public FractalWorker(int y_coord) {
            this.y_coord = y_coord;
        }

        @Override
        protected Object doInBackground() throws Exception {
            rgb = new int[displaySize];
            for (int i = 0; i < displaySize; i++) {
                int count = fractalGenerator.numIterations(
                        FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, i),
                        FractalGenerator.getCoord(range.y, range.y + range.width, displaySize, y_coord));
                if (count == -1)
                    rgb[i] = 0;
                else {
                    double hue = 0.7f + (float) count / 200f;
                    int rgbColor = Color.HSBtoRGB((float) hue, 1f, 1f);
                    rgb[i] = rgbColor;
                }
            }
            return null;
        }

        @Override
        protected void done() {
            for (int i = 0; i < displaySize; i++) {
                imageDisplay.drawPixel(i, y_coord, rgb[i]);
            }
            imageDisplay.repaint(0, 0, y_coord, displaySize, 1);
            rowsRemaining--;
            if (rowsRemaining == 0)
                enableGUI(true);
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

        FractalExplorer displayExplorer = new FractalExplorer(700);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}