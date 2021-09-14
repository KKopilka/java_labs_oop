import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args) {

        /* Создаём 3 объекта */
        Point3d FirstPoint = new Point3d();
        Point3d SecondPoint = new Point3d();
        Point3d ThirdPoint = new Point3d();

        Scanner in = new Scanner(System.in); // Инициализируем сканер для работы данными через ввод с клавиатуры

        for (int i = 1; i < 4; i++) {
            Point3d object;
            String name;
            if (i == 1) {
                object = FirstPoint;
                name = "A Point: ";
            } else if (i == 2) {
                object = SecondPoint;
                name = "B Point: ";
            } else {
                object = ThirdPoint;
                name = "C Point: ";
            }

            /* Коррдината X для точек */
            System.out.print("Input a xCoord for " + name);
            double x = in.nextInt();
            object.setX(x);

            /* Коррдината Y для точек */
            System.out.print("Input a yCoord for " + name);
            double y = in.nextInt();
            object.setY(y);

            /* Коррдината Z для точек */
            System.out.print("Input a zCoord for " + name);
            double z = in.nextInt();
            object.setZ(z);

            /* Вывод */
            System.out.println(
                    "Coordinates for " + name + "(" + object.getX() + "," + object.getY() + "," + object.getZ() + ")");
        }

        in.close(); // Закрываем сканер

        if (FirstPoint.IsTheSameEquals(SecondPoint)) { // Проверяем, что координаты точки A == B
            System.out.println("First Point and Second Point have the same coordinates.");
        } else if (FirstPoint.IsTheSameEquals(ThirdPoint)) { // Проверяем, что координаты точки A == C
            System.out.println("First Point and Third Point have the same coordinates.");
        } else if (SecondPoint.IsTheSameEquals(ThirdPoint)) { // Проверяем, что координаты точки B == C
            System.out.println("Second Point and Third Point have the same coordinates.");
        } else {
            System.out.println("Triangle Square = " + computeArea(FirstPoint, SecondPoint, ThirdPoint)); // Выводим
                                                                                                         // площадь
        }
    }

    /** Вычисляем площадь треугольника по трём точкам (формула Герона) **/
    public static double computeArea(Point3d obj1, Point3d obj2, Point3d obj3) {
        double a = obj1.distanceTo(obj2); // Расстояние между точкой A и B = a
        double b = obj1.distanceTo(obj3); // Расстояние между точкой A и C = b
        double c = obj2.distanceTo(obj3); // Расстояние между точкой B и C = c

        /* Вычисляем полупериметр */
        double p = (a + b + c) / 2;
        /* Вычисляем площадь по формуле Герона */
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}