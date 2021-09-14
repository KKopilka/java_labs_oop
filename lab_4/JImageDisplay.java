import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class JImageDisplay extends JComponent {

    /**
     * экземпляр BufferImage класс управляет изображением, содержимое которого можно
     * записать
     **/
    private BufferedImage displayImage;

    /**
     * @return возвращает изображение полученное из другого класса
     **/
    public BufferedImage getImage() {
        return displayImage;
    }

    /**
     * Задание ширины и высоты изображения и определение цветовой схемы изображения.
     * 
     * @param wight  - ширина изображения
     * @param height - высота изображения
     **/
    /*
     * TYPE_INT_RGB: Тип определяет, как цвета каждого пикселя будут представлен в
     * изображении; значение TYPE_INT_RGB обозначает, что красные, зеленые и синие
     * компоненты имеют по 8 битов, представленные в формате int в указанном
     * порядке.
     */
    public JImageDisplay(int wight, int height) {
        displayImage = new BufferedImage(wight, height, BufferedImage.TYPE_INT_RGB);

        /**
         * Вызов метода setPreferredSize() из родительского класса с указанными шириной
         * и высотой
         **/
        Dimension imageDimension = new Dimension(wight, height);
        super.setPreferredSize(imageDimension);
    }

    /**
     * Переопределение компонента paintComponent для использования собственного кода
     * отрисовки
     * 
     * @param graphics - защищаемый графический объект
     **/
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(displayImage, 0, 0, displayImage.getWidth(), displayImage.getHeight(), null);
    }

    /**
     * Устанавка всех пикселей изображения в черный цвет
     **/
    public void clearImage() {
        int[] blankArray = new int[getWidth() * getHeight()];
        displayImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
    }

    /**
     * Установка пикселя в определенный цвет
     * 
     * @param x        - координаты x
     * @param y        - координата y
     * @param rgbColor - цвет пикселя
     **/
    public void drawPixel(int x, int y, int rgbColor) {
        displayImage.setRGB(x, y, rgbColor);
    }
}