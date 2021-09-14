import java.net.*;

public class URLDepthPair {
    private String url;
    private int parseDeep;

    public URLDepthPair(String str, int num) {
        url = str;
        parseDeep = num;
    }

    public String getUrl() {
        return url;
    }

    public int getParseDeep() {
        return parseDeep;
    }

    public String toString() {
        String str = Integer.toString(parseDeep) + ": " + url;
        return str;
    }

    public String getDocPath() {
        try {
            URL link = new URL(url);
            return link.getPath();
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }

    public String getWebHost() {
        try {
            URL link = new URL(url);
            return link.getHost();
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }
}