package bd.univdhaka.cse2216.learnyorself;

public class User {
    private String name;
    private String profession;
    private String institution;
    private String profilePicUrl;
    private String email;
    private String userId;


    public User(String name, String profession, String institution,String profilePicUrl,String userId ,String email) {
        this.name = name;
        this.profession = profession;
        this.institution = institution;
        this.email=email;
        this.userId=userId;
        this.profilePicUrl=profilePicUrl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;

    }

    public String getUserId() {
        return userId;
    }

    public String getProfilePicUrl(){
        return profilePicUrl;
    }
    public void setProfilePicUrl(String url){
        profilePicUrl=url;
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



    public void setName(String name) {
        this.name = name;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }


}
