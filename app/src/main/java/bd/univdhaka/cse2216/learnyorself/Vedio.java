package bd.univdhaka.cse2216.learnyorself;

public class Vedio {
    private String url;
    private String title;
    private String tag;
    private String uploadDate;
    private String owner;

    public Vedio(String url, String title, String tag, String owner,String uploadDate) {
        this.url = url;
        this.title = title;
        this.owner=owner;
        this.tag = tag;
        this.uploadDate = uploadDate;
    }
    public void setOwner(String owner){
        this.owner=owner;
    }

    public String getOwner() {
        return owner;
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
}
