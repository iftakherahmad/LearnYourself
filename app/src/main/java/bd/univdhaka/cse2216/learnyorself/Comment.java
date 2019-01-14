package bd.univdhaka.cse2216.learnyorself;

public class Comment {
    private int like;
    private int dislike;
    private String imageUrls;
    private String owner;
    private String postContentSequence;
    private String textUrl;
    private int weight;
    private String time;

    public Comment(int like, int dislike, String imageUrls, String owner, String postContentSequence, String textUrl, int weight,String time) {
        this.like = like;
        this.dislike = dislike;
        this.imageUrls = imageUrls;
        this.owner = owner;
        this.postContentSequence = postContentSequence;
        this.textUrl = textUrl;
        this.weight = weight;
        this.time=time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPostContentSequence(String postContentSequence) {
        this.postContentSequence = postContentSequence;
    }

    public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public String getOwner() {
        return owner;
    }

    public String getPostContentSequence() {
        return postContentSequence;
    }

    public String getTextUrl() {
        return textUrl;
    }

    public int getWeight() {
        return weight;
    }
}
