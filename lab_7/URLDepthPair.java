import java.util.LinkedList;
import java.net.MalformedURLException;
import java.net.URL;

/** Класс для представления пар [URL, глубина] для нашего сканера. **/
public class URLDepthPair {
    public static final String URL_PREFIX = "http://";

    /** Поля для представления текущего URL и текущей глубины. **/
    String URL;
    public int depth;

    /** Конструктор, который устанавливает ввод для текущего URL и глубины. **/
    public URLDepthPair(String URL, int depth) {
        this.URL = URL;
        this.depth = depth;
    }

    /** Метод, который возвращает Host текущего URL. **/
    public String getHost() throws MalformedURLException {
        URL host = new URL(URL);
        return host.getHost();
    }

    /** Метод, который возвращает Path текущего URL. **/
    public String getPath() throws MalformedURLException {
        URL path = new URL(URL);
        return path.getPath();
    }

    /** Метод, который возвращает текущую глубину. **/
    public int getDepth() {
        return depth;
    }

    /** Метод, который возвращает текущий URL. **/
    public String getURL() {
        return URL;
    }

    public static boolean check(LinkedList<URLDepthPair> resultLink, URLDepthPair pair) {
        boolean isAlready = true;
        for (URLDepthPair c : resultLink)
            if (c.getURL().equals(pair.getURL()))
                isAlready = false;
        return isAlready;
    }
}