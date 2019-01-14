package bd.univdhaka.cse2216.learnyorself;

public class User {
    private String name;
    private String profession;
    private String institution;
    private String profilePicUrl;
    private String signupDate;
    private String email;
    private String userId;
    private int postiveReview;
    private int negetiveReview;
    private int rewardPoint;

    public User(String name, String profession, String institution, String profilePicUrl, String signupDate, String email, String userId, int postiveReview, int negetiveReview, int rewardPoint) {
        this.name = name;
        this.profession = profession;
        this.institution = institution;
        this.profilePicUrl = profilePicUrl;
        this.signupDate = signupDate;
        this.email = email;
        this.userId = userId;
        this.postiveReview = postiveReview;
        this.negetiveReview = negetiveReview;
        this.rewardPoint = rewardPoint;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPostiveReview(int postiveReview) {
        this.postiveReview = postiveReview;
    }

    public void setNegetiveReview(int negetiveReview) {
        this.negetiveReview = negetiveReview;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getName() {
        return name;
    }

    public String getProfession() {
        return profession;
    }

    public String getInstitution() {
        return institution;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public int getPostiveReview() {
        return postiveReview;
    }

    public int getNegetiveReview() {
        return negetiveReview;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }
}
