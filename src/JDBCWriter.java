import java.sql.*;
import java.util.ArrayList;

public class JDBCWriter {
    private Connection connection;

    public  boolean setConnection() {
        final String url = "jdbc:mysql://127.0.0.1:3306/rssfeed?serverTimezone=UTC";
        boolean res = false;
        try {
            connection = DriverManager.getConnection(url, "Yrsa", "1234");
            res = true;
        } catch (SQLException err) {
            System.out.println("Vi fik IKKE connection, err =" + err.getMessage());
        }
            return res;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countFeeds() {
        String str = "SELECT count(*) FROM feeds";
        PreparedStatement preparedStatement;
        int result = -1;
        try {
            preparedStatement = connection.prepareStatement(str);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                /*
                String str2 = "" + resultSet.getObject(1);
                result = Integer.parseInt(str2);
               En anden måde end det nedenunder
                 */
                result = resultSet.getInt(1);
            }
        } catch (SQLException err) {
            System.out.println("Fejl i count, err=" + err.getMessage());
        }
        return result;
    }

    public int countTabel(String table) {
        String str = "SELECT count(*) FROM " + table;
        PreparedStatement preparedStatement;
        int result = -1;
        try {
            preparedStatement = connection.prepareStatement(str);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException err) {
            System.out.println("Fejl i count af tabel: err =" + err.getMessage());
        }
        return result;
    }

    public int writeFeed(RSSUrl rssUrl, Feed feed) {
        //Insert
        String insertstr = "INSERT into feeds(feed_id, feedurl, title, link, description, language,copyright, pubdate) values (?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement;
        int rowcount = 0;
        try{
            preparedStatement = connection.prepareStatement(insertstr);
            preparedStatement.setInt(1, rssUrl.getFeedId());
            preparedStatement.setString(2, rssUrl.getRssUrl());
            preparedStatement.setString(3, feed.getTitle());
            preparedStatement.setString(4, feed.getLink());
            preparedStatement.setString(5, feed.getDescription());
            preparedStatement.setString(6, feed.getLanguage());
            preparedStatement.setString(7, feed.getCopyright());
            preparedStatement.setString(8, feed.getPubDate());
            int ii = preparedStatement.executeUpdate();
            rowcount += ii;
        }catch (SQLException err) {
            System.out.println("FEJL i insert feed err =" + err.getMessage());
        }
        return rowcount;
    }

    public int writeFeedMessages(RSSUrl rssUrl, FeedMessage feedMessage) {
        //insert into feedmessages(feed_id, title, description, guid) values
        String insertstr = "INSERT into feedmessages(feed_id, guid, title, description, link, author) values (?,?,?,?,?,?)";
        PreparedStatement preparedStatement;
        int rowcount = 0;
        try{
            preparedStatement = connection.prepareStatement(insertstr);
            preparedStatement.setInt(1, rssUrl.getFeedId());
            preparedStatement.setString(2, feedMessage.getGuid());
            preparedStatement.setString(3, feedMessage.getTitle());
            preparedStatement.setString(4, feedMessage.getDescription());
            preparedStatement.setString(5, feedMessage.getLink());
            preparedStatement.setString(6, feedMessage.getAuthor());
            int ii = preparedStatement.executeUpdate();
            rowcount += ii;
        }catch (SQLException err) {
            System.out.println("FEJL i insert feed err =" + err.getMessage());
        }
        return rowcount;
    }

    public ArrayList<FeedDTO> readFeedDTO(String searchWord) {
        String search = "SELECT  feed_id, feedurl, fm1.title, fm1.link, fm1.description FROM feeds f1 ";
        search += "JOIN feedmessages fm1 using(feed_id) ";
        search += "WHERE f1.title like ? OR fm1.title like ?";

        ArrayList<FeedDTO> feedDTOS = new ArrayList<>();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(search);
            preparedStatement.setString(1, "%" + searchWord + "%");
            preparedStatement.setString(2, "%" + searchWord + "%");
            //resultset kender også kolonnerne
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                FeedDTO dto = new FeedDTO();
                dto.setFeedid(resultSet.getInt(1));
                dto.setFeedurl(resultSet.getString("feedurl"));
                dto.setTitle(resultSet.getString("title"));
                dto.setDesciption(resultSet.getString("description"));
                dto.setDesciption(resultSet.getString("link"));
                feedDTOS.add(dto);
            }
        } catch (SQLException err) {
            System.out.println("Fejl i count err=" + err.getMessage());
        }
        return feedDTOS;
    }
}