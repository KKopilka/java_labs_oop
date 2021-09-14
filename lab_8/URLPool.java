import java.util.*;

public class URLPool {

    /** Связанный список для представления ожидающих URL-адресов. **/
    private LinkedList<URLDepthPair> indexingSites;
    /** Связанный список для представления обработанных URL. **/
    public LinkedList<URLDepthPair> remainingSites;
    /** Список для представления увиденных URL. **/
    private LinkedList<String> urls = new LinkedList<String>();
    public int waitingThreads;
    private int maxDepth;

    /**
     * Конструктор для инициализации ожидающих потоков, обработанных URL-адресов и
     * ожидающих URL-адресов.
     **/
    public URLPool(int maxDepth) {
        waitingThreads = 0;
        indexingSites = new LinkedList<URLDepthPair>();
        remainingSites = new LinkedList<URLDepthPair>();
        this.maxDepth = maxDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    /** Синхронизированный метод для получения количества ожидающих потоков. **/
    public synchronized int getWaitThreads() {
        return waitingThreads;
    }

    /** Синхронизированный метод для возврата размера пула. **/
    public synchronized int sizeRemaing() {
        return remainingSites.size();
    }

    public synchronized int sizeInd() {
        return indexingSites.size();
    }

    public synchronized boolean isIndexed(URLDepthPair Pair) {
        if (indexingSites.contains(Pair)) {
            return true;
        }
        return false;
    }

    public synchronized int getIndexNumber(URLDepthPair Pair) {
        return indexingSites.indexOf(Pair);
    }

    public synchronized void indexing(URLDepthPair Pair) {
        indexingSites.add(Pair);
    }

    /** Синхронизированный метод для добавления пары глубин в пул. **/

    public synchronized void putPairList(LinkedList<URLDepthPair> list) {
        if (list.size() != 0) {
            for (URLDepthPair pair : list) {
                if (!remainingSites.contains(pair)) {
                    remainingSites.addLast(pair);
                }
            }
            waitingThreads--;
            this.notify();
        }
    }

    public synchronized void put(URLDepthPair Pair) {

        if (!remainingSites.contains(Pair)) {
            remainingSites.addLast(Pair);
        }
    }

    /** Синхронизированный метод для получения следующей пары глубин из пула. **/
    public synchronized URLDepthPair get() {

        URLDepthPair currentPair;

        if (remainingSites.size() == 0) {
            waitingThreads++;
            try {
                this.wait();
            } catch (InterruptedException e) {
                // System.err.println("MalformedURLException: " + e.getMessage());
            }
            return null;
        }

        currentPair = remainingSites.pop();
        remainingSites.remove(currentPair);
        return currentPair;
    }

    public synchronized LinkedList<String> getSeenList() {
        return urls;
    }

    /** Синхронизированный метод, чтобы получить список увиденных URL-адресов. **/
    public synchronized void putInSeenList(String url) {
        urls.add(url);
    }

    public void writeAllIndexed() {
        for (int i = 0; i < indexingSites.size(); i++) {
            System.out.println(indexingSites.get(i).getUrl());
        }
        System.out.println("All indexed ursl: " + Integer.toString(indexingSites.size()));
    }
}