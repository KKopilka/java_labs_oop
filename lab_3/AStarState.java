import java.util.*;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map. This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints." In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/

public class AStarState {
    /** Ths is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    /** Инициализация карты всех открытых точек и их положений **/
    private Map<Location, Waypoint> open_waypoints = new HashMap<Location, Waypoint>();

    /** Инициализация карты всех закрытых точек и их положений **/
    private Map<Location, Waypoint> closed_waypoints = new HashMap<Location, Waypoint>();

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map) {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;

    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap() {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint with
     * the minimum total cost. If there are no open waypoints, this method returns
     * <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint() {
        // вернем NULL если в наборе нет открытых точек
        if (numOpenWaypoints() == 0)
            return null;

        /**
         * Инициализация набора ключей всех открытых путевых точек, интератор (?) для
         * итерации через набор и переменная для удержания лучшей путевой точки и
         * стоимости для этой путевой точки
         **/
        // установим параметры для отбора меньшей вершины
        Set open_waypoint_keys = open_waypoints.keySet();
        Iterator i = open_waypoint_keys.iterator();
        Waypoint best = null;
        float best_cost = Float.MAX_VALUE;

        /**
         * Сканирует через все открытые путевые точки
         **/
        // цикл для перебора вершин в коллекции
        while (i.hasNext()) {
            /** Хранит текущее местоположение **/
            Location location = (Location) i.next();

            /** Хранит текущую путевую точку **/
            Waypoint waypoint = open_waypoints.get(location);

            /** Хранит общую стоимость для текущих путевых точек **/
            float waypoint_total_cost = waypoint.getTotalCost();

            /**
             * Если общая стоимость для текущей путевой точки лучше (ниже), чем сохраненная
             * стоимость для сохраненной наилучшей путевой точки, замените сохраненную
             * путевую точку на текущую путевую точку и сохраненную общую стоимость с
             * текущей общей стоимостью.
             **/
            if (waypoint_total_cost < best_cost) {
                best = open_waypoints.get(location);
                best_cost = waypoint_total_cost;
            }

        }

        /** Возврат путевой точки с минимальной общей стоимостью **/
        return best;
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already in)
     * the "open waypoints" collection. If there is not already an open waypoint at
     * the new waypoint's location then the new waypoint is simply added to the
     * collection. However, if there is already a waypoint at the new waypoint's
     * location, the new waypoint replaces the old one <em>only if</em> the new
     * waypoint's "previous cost" value is less than the current waypoint's
     * "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP) {
        /** Находит местоположение новой пуевой точки **/
        Location location = newWP.getLocation();

        /**
         * Проверяет, есть ли уже открытая путевая точка в местоположении новой путевой
         * точки.
         **/
        if (open_waypoints.containsKey(location)) {

            /**
             * Если уже есть открытая путевая точка в местоположении новой путевой точки,
             * проверяется, меньше ли значение "предыдущей стоимости" новой путевой точки,
             * чем значение "предыдущей стоимости" текущей путевой точки.
             **/
            Waypoint current_waypoint = open_waypoints.get(location);
            if (newWP.getPreviousCost() < current_waypoint.getPreviousCost()) {

                /**
                 * Если значение «предыдущей стоимости» новой путевой точки меньше значения
                 * «предыдущей стоимости» текущей путевой точки, новая путевая точка заменяет
                 * старую путевую точку и возвращает true.
                 **/
                open_waypoints.put(location, newWP);
                return true;
            }

            /**
             * Если значение «предыдущей стоимости» новой путевой точки не меньше, чем
             * значение «предыдущей стоимости» текущей путевой точки, вернуть false.
             **/
            return false;
        }

        /**
         * Если в местоположении новой путевой точки еще нет открытой путевой точки,
         * добавьте новую путевую точку в коллекцию открытых путевых точек и верните
         * true.
         **/
        open_waypoints.put(location, newWP);
        return true;
    }

    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints() {
        return open_waypoints.size();
    }

    /**
     * This method moves the waypoint at the specified location from the open list
     * to the closed list.
     **/
    public void closeWaypoint(Location loc) {
        Waypoint waypoint = open_waypoints.remove(loc);
        closed_waypoints.put(loc, waypoint);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint for
     * the specified location.
     **/
    public boolean isLocationClosed(Location loc) {
        return closed_waypoints.containsKey(loc);
    }
}
