package bd.univdhaka.cse2216.learnyorself;

public class Post {
    private String postContentSequence;
    private String title;
    private String tag;
    private String time;
    private String textUrl;
    private String imageUrls;
    private String owner;
    private String like;
    private String dislike;
   // private String profilePic;

    public Post(String postContentSequence, String title, String tag, String time, String textUrl, String imageUrls, String owner, String like, String dislike) {
        this.title = title;
        this.tag = tag;
        this.postContentSequence=postContentSequence;
        this.time = time;
        this.textUrl = textUrl;
        this.imageUrls = imageUrls;
        this.owner = owner;
        this.like = like;
        this.dislike = dislike;
    }

    public String getPostContentSequence() {
        return postContentSequence;
    }

    public void setPostContentSequence(String postContentSequence) {
        this.postContentSequence = postContentSequence;
    }

    public String getTitle() {
        return title;
    }

    public String getTag() {
        return tag;
    }

    public String getTime() {
        return time;
    }

    public String getTextUrl() {
        return textUrl;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public String getOwner() {
        return owner;
    }

    public String getLike() {
        return like;
    }

    public String getDislike() {
        return dislike;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }
}
