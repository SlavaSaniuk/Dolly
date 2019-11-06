package testentities;

public class UserEntity {

    private Integer user_id;

    private String user_alias;

    private DetailsEntity user_details;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_alias() {
        return user_alias;
    }

    public void setUser_alias(String user_alias) {
        this.user_alias = user_alias;
    }

    public DetailsEntity getUser_details() {
        return user_details;
    }

    public void setUser_details(DetailsEntity user_details) {
        this.user_details = user_details;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "user_id=" + user_id +
                ", user_alias='" + user_alias + '\'' +
                ", user_details=" + user_details +
                '}';
    }
}
