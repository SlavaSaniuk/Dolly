import by.bsac.annotations.Dto;

@Dto({User.class})
public class UserDto {

    private Integer user_id;

    private String user_id_alias;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_id_alias() {
        return user_id_alias;
    }

    public void setUser_id_alias(String user_id_alias) {
        this.user_id_alias = user_id_alias;
    }
}
