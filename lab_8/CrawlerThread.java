import java.io.IOException;
import java.net.*;
import java.util.*;

public class CrawlerThread extends Thread {
    public static final String URL_PROTOCOL = "http://";
    public static final String URL_PREFIX = "href=\"";
    public static final String END_URL = "\"";
    public static final String[] WRONG_URL_ENDS = { ".apk", ".ogv" };

    LinkedList<URLDepthPair> newURLDepthPairs = new LinkedList<URLDepthPair>();
    URLDepthPair currentPair;
    LinkedList<String> urls;
    URLPool myPool;

    public CrawlerThread(URLPool pool) {
        myPool = pool;
    }

    /** Метод для запуска задач. **/
    @Override
    public void run() {
        /** Получить следующую пару глубин из пула. **/
        currentPair = myPool.get();
        if (currentPair == null) {
            return;
        }
        urls = myPool.getSeenList();
        LinkedList<URLDepthPair> newURLDepthPairs = new LinkedList<URLDepthPair>();
        if (myPool.isIndexed(currentPair)) {
            return;
        }
        if (currentPair.getParseDeep() == myPool.getMaxDepth()) {
            return;
        }
        myPool.indexing(currentPair);
        System.out.println("now, the " + currentPair.toString() + " was indexed It's number is: "
                + Integer.toString(1 + myPool.getIndexNumber(currentPair)) + "/"
                + Integer.toString(myPool.sizeInd() + myPool.sizeRemaing()));
        URLConnection connection;
        try {
            connection = new URL(currentPair.getUrl()).openConnection();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            myPool.putPairList(newURLDepthPairs);
            return;
        }
        Scanner scanner;
        try {
            scanner = new Scanner(connection.getInputStream());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            myPool.putPairList(newURLDepthPairs);
            return;
        }

        scanner.useDelimiter("\\Z");
        String line = "";
        if (scanner.hasNext()) {
            line = scanner.next();
        }
        scanner.close();
        int startIndex = 0;
        int endIndex = 0;
        while (true) {
            startIndex = line.indexOf(URL_PREFIX, endIndex);
            endIndex = line.indexOf(END_URL, startIndex + URL_PREFIX.length());
            if (startIndex == -1 || endIndex == -1) {
                break;
            }
            if (endIndex - startIndex > 200) {
                String temp = line.substring(startIndex, startIndex + URL_PREFIX.length());
                line.replaceFirst(temp, "");
                continue;
            }
            String newLink = line.substring(startIndex + 6, endIndex);
            if (!newLink.contains(URL_PROTOCOL)) {
                continue;
            }
            boolean isBadLink = false;
            for (Object urlEnd : WRONG_URL_ENDS) {
                if (newLink.substring(newLink.length() - 4, newLink.length()).equals(urlEnd)) {
                    isBadLink = true;
                    break;
                }

            }
            if (isBadLink) {
                continue;
            }

            if (urls.contains(newLink)) {
                continue;
            }
            /** Создаём новую пару глубин для каждой найденной ссылки и добавляем в пул. **/
            myPool.putInSeenList(newLink);
            if (myPool.getMaxDepth() > currentPair.getParseDeep() + 1) {
                URLDepthPair newURLDepthPair = new URLDepthPair(newLink, currentPair.getParseDeep() + 1);
                newURLDepthPairs.add(newURLDepthPair);
            }
        }
        myPool.putPairList(newURLDepthPairs);

    }

}