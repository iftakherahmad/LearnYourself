package bd.univdhaka.cse2216.learnyorself;

public class NotificationObject {
    private String involvedPerson;
    private String involvedPost;
    private String time;
    private String message;
    private String postType;
    public String getInvolvedPerson() {
        return involvedPerson;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public void setInvolvedPerson(String involvedPerson) {
        this.involvedPerson = involvedPerson;
    }

    public void setInvolvedPost(String involvedPost) {
        this.involvedPost = involvedPost;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInvolvedPost() {
        return involvedPost;

    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public  NotificationObject(){}

    public NotificationObject(String involvedPerson, String involvedPost, String time, String message,String type) {
        this.involvedPerson = involvedPerson;
        this.involvedPost = involvedPost;
        this.time = time;
        this.message=message;
        this.postType=type;

    }
}
