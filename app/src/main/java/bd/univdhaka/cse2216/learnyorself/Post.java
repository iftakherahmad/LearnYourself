package bd.univdhaka.cse2216.learnyorself;

public class Post {
    private String postContentSequence;
    private String title;
    private String tag;
    private String time;
    private String textUrl;
    private String imageUrls;
    private String owner;
    private int like;
    private int dislike;
    private String reverseUid;
    private String title_tag;
    private int ansers;
   // private String profilePic;

    public Post(String postContentSequence, String title, String tag, String time, String textUrl, String imageUrls, String owner, int like, int dislike, String reverseUid, String title_tag, int ansers) {
        this.postContentSequence = postContentSequence;
        this.title = title;
        this.tag = tag;
        this.time = time;
        this.textUrl = textUrl;
        this.imageUrls = imageUrls;
        this.owner = owner;
        this.like = like;
        this.dislike = dislike;
        this.reverseUid = reverseUid;
        this.title_tag = title_tag;
        this.ansers = ansers;
    }

    public void setPostContentSequence(String postContentSequence) {
        this.postContentSequence = postContentSequence;
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

    public void setLike(int like) {
        this.like = like;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public void setReverseUid(String reverseUid) {
        this.reverseUid = reverseUid;
    }

    public void setTitle_tag(String title_tag) {
        this.title_tag = title_tag;
    }

    public void setAnsers(int ansers) {
        this.ansers = ansers;
    }

    public String getPostContentSequence() {
        return postContentSequence;
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

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }

    public String getReverseUid() {
        return reverseUid;
    }

    public String getTitle_tag() {
        return title_tag;
    }

    public int getAnsers() {
        return ansers;
    }
}
