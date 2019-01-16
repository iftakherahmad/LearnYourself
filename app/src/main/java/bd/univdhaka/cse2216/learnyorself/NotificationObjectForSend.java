package bd.univdhaka.cse2216.learnyorself;

public class NotificationObjectForSend {
    private String involvedPerson;
    private String involvedPost;
    private String time;
    private String message;
    private String postType;

    public NotificationObjectForSend(String involvedPerson, String involvedPost, String time, String message,String postType) {
        this.involvedPerson = involvedPerson;
        this.involvedPost = involvedPost;
        this.time = time;
        this.message = message;
        this.postType=postType;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getInvolvedPerson() {
        return involvedPerson;
    }

    public void setInvolvedPerson(String involvedPerson) {
        this.involvedPerson = involvedPerson;
    }

    public String getInvolvedPost() {
        return involvedPost;
    }

    public void setInvolvedPost(String involvedPost) {
        this.involvedPost = involvedPost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
