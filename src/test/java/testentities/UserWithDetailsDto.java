package testentities;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoProperty;

@Dto({UserEntity.class, DetailsEntity.class})
public class UserWithDetailsDto {

    private Integer user_id;

    private String user_alias;

    @DtoProperty(entityProperty = "detail_id")
    private Integer details_id;

    @DtoProperty(entityProperty = "user_details")
    private DetailsEntity details;

    @DtoProperty(entityProperty = "detail_name")
    private String user_name;

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

    public Integer getDetails_id() {
        return details_id;
    }

    public void setDetails_id(Integer details_id) {
        this.details_id = details_id;
    }

    public DetailsEntity getDetails() {
        return details;
    }

    public void setDetails(DetailsEntity details) {
        this.details = details;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "UserWithDetailsDto{" +
                "user_id=" + user_id +
                ", user_alias='" + user_alias + '\'' +
                ", details_id=" + details_id +
                ", details=" + details +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
