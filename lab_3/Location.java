
/**
 * This class represents a specific location in a 2D map. Coordinates are
 * integer values.
 **/

public class Location {
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;

    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location() {
        this(0, 0);
    }

    /**
     * equals проверка на совпадение для двух точек (по обоим значениям X и Y)
     **/
    /** сравнение позиций **/
    public boolean equals(Object obj) {

        // Проверим, является ли объект Location
        if (obj instanceof Location) {

            Location other = (Location) obj;
            if (xCoord == other.xCoord && yCoord == other.yCoord) {
                return true;
            }
        }

        return false;
    }

    /**
     * Метод hashCode. В будущем, позволяет проверить, что 2 объекта примерно (не
     * равны), дабы ускорить работу программы в качестве реализации взят код,
     * который много где описан для реализации этого метода
     **/
    /** получение хеш-кода для каждой позиции **/
    public int hashCode() {

        // случайное число
        int result = 13;

        // второе случайное число и вычисление
        result = 17 * result + xCoord;
        result = 17 * result + yCoord;

        return result;
    }
}
