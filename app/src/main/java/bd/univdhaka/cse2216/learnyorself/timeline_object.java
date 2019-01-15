package bd.univdhaka.cse2216.learnyorself;

public class timeline_object {
    private String titile;
    private String tag;
    private String text;
    private String profilePic;
    private String ownerName;
    private String date;
    private String contentImage;
    private String postId;
    private String postType;
    private String path;
    public timeline_object(){

    }
    public timeline_object(String postType,String titile, String tag, String text, String profilePic, String ownerName, String date, String contentImage, String postId,String path) {
        this.titile = titile;
        this.tag = tag;
        this.postType=postType;
        this.text = text;
        this.profilePic = profilePic;
        this.ownerName = ownerName;
        this.date = date;
        this.contentImage = contentImage;
        this.postId = postId;
        this.path=path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPostType(String postType){
        this.postType=postType;
    }
    public String getPostType(){
        return postType;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitile() {
        return titile;
    }

    public String getTag() {
        return tag;
    }

    public String getText() {
        return text;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getDate() {
        return date;
    }

    public String getContentImage() {
        return contentImage;
    }

    public String getPostId() {
        return postId;
    }
}
