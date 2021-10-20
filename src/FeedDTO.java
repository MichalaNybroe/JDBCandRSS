public class FeedDTO {
    //DTO Data transffer Object: transfers data from database to frontend
    private int feedid;
    private String feedurl;
    private String title;
    private String desciption;
    private String link;

    public int getFeedid() {
        return feedid;
    }

    public void setFeedid(int feedid) {
        this.feedid = feedid;
    }

    public String getFeedurl() {
        return feedurl;
    }

    public void setFeedurl(String feedurl) {
        this.feedurl = feedurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "FeedDTO{" +
                "feedid=" + feedid +
                ", feedurl='" + feedurl + '\'' +
                ", title='" + title + '\'' +
                ", desciption='" + desciption + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
