import java.util.ArrayList;

public class RSSUrl {

    private int feedId;
    private String rssUrl;

    public static ArrayList<RSSUrl> getNews() {
        ArrayList<RSSUrl> rss = new ArrayList<>();
        rss.add(new RSSUrl(1, "https://ekstrabladet.dk/rssfeed/musik/"));
        rss.add(new RSSUrl(2, "https://ekstrabladet.dk/rssfeed/kendte/"));
        rss.add(new RSSUrl(3, "http://rss.cnn.com/rss/edition_world.rss"));

        return rss;
    }

    public RSSUrl(int feedId, String rssUrl) {
        this.feedId = feedId;
        this.rssUrl = rssUrl;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    @Override
    public String toString() {
        return "RSSUrl{" +
                "feedId=" + feedId +
                ", rssUrl='" + rssUrl + '\'' +
                '}';
    }
}
