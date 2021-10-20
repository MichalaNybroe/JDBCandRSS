import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JDBCWriter jdbcWriter = new JDBCWriter();
        boolean hasCon = jdbcWriter.setConnection();
        System.out.println("Har con hascon=" + hasCon);

        if (hasCon) {
            int feedCount = jdbcWriter.countFeeds();
            System.out.println("Der var " + feedCount + " feeds");
        }


        ArrayList<RSSUrl> urls = RSSUrl.getNews();
        for (RSSUrl rss : urls) {
            System.out.println(rss);
            // læse fra sky og putte det i database
            RSSFeedParser parser = new RSSFeedParser(rss.getRssUrl());

            Feed feed = parser.readFeed();

            System.out.println("FEED læst =" + feed);

            int ii = jdbcWriter.writeFeed(rss, feed);
            System.out.println("WriteFeed kaldt i =" + ii);

            for (FeedMessage msg : feed.getMessages()) {
                //System.out.println(msg);
                int imsg = jdbcWriter.writeFeedMessages(rss, msg);
                System.out.println("feedmessages written imsg=" + imsg);
            }
        }

        // counting a table of ur choosing
        if (hasCon) {
            int feedCount1 = jdbcWriter.countTabel("feeds");
            int feedmessagesCount1 = jdbcWriter.countTabel("feedmessages");
            System.out.println("Der var " + feedCount1 + " feeds");
            System.out.println("Der var " + feedmessagesCount1 + " feedmessages");

            int feedCount2 = jdbcWriter.countTabel("feeds");
            int feedmessagesCount2 = jdbcWriter.countTabel("feedmessages");
            System.out.println("Der var " + feedCount2 + " feeds i anden læsning.");
            System.out.println("En forskel af " + (feedCount2 - feedCount1) + ".");
            System.out.println("Der var " + feedmessagesCount2 + " feedmessages i anden læsning.");
            System.out.println("En forskel af " + (feedmessagesCount2 - feedmessagesCount1) + ".");
        }

        // Titles fra database
        ArrayList<FeedDTO> feeds;
        feeds = jdbcWriter.readFeedDTO("space");
        FeedDTO dto = feeds.get(0);
        int feedcount = dto.getFeedid();
        System.out.println("Jeg har " + feedcount + " feeds indeholdene dette ord.");

        for (FeedDTO feed : feeds) {
            System.out.println(feed);
        }
        jdbcWriter.closeConnection();
    }
}
