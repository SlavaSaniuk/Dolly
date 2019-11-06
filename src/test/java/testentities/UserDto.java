package testentities;

import by.bsac.annotations.Dto;

@Dto({UserEntity.class})
public class UserDto {

    private Integer user_id;

    private String user_alias;

    private DetailsEntity details;
}
