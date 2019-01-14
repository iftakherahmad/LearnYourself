package bd.univdhaka.cse2216.learnyorself;

public class Vedio {
    private String url;
    private String title;
    private String tag;
    private String uploadDate;
    private String owner;
    private  int like;
    private int dislike;
    private int  weight;
    private int comments;

    public Vedio(String url, String title, String tag, String uploadDate, String owner, int like, int dislike, int weight, int comments) {
        this.url = url;
        this.title = title;
        this.tag = tag;
        this.uploadDate = uploadDate;
        this.owner = owner;
        this.like = like;
        this.dislike = dislike;
        this.weight = weight;
        this.comments = comments;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getTag() {
        return tag;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getOwner() {
        return owner;
    }

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }

    public int getWeight() {
        return weight;
    }

    public int getComments() {
        return comments;
    }
}
