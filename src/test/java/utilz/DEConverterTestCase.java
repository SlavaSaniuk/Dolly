package utilz;

import by.bsac.core.DEConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testentities.DetailsEntity;
import testentities.UserEntity;
import testentities.UserWithDetailsDto;

class DEConverterTestCase {

    @Test
    void toDto_userEntity_shouldReturnDto() {
        UserEntity user = new UserEntity();
        user.setUser_id(24);
        user.setUser_alias("alias");
        DetailsEntity details = new DetailsEntity();
        details.setDetail_id(25);
        details.setDetail_name("name");
        user.setUser_details(details);

        System.out.println(user.toString());
        System.out.println(details.toString());

        UserWithDetailsDto dto = DEConverter.toDto(user, new UserWithDetailsDto());
        dto = DEConverter.toDto(details, dto);

        Assertions.assertEquals(user.getUser_id(), dto.getUser_id());
        Assertions.assertEquals(user.getUser_alias(), dto.getUser_alias());
        Assertions.assertEquals(user.getUser_details(), dto.getDetails());
        Assertions.assertEquals(details.getDetail_id(), dto.getDetails_id());
        Assertions.assertEquals(details.getDetail_name(), dto.getUser_name());

        System.out.println(dto.toString());
    }

    @Test
    void toEntity_dtoObject_shouldReturnEntity() {

        UserWithDetailsDto dto = new UserWithDetailsDto();
        dto.setUser_id(46);
        dto.setDetails_id(23);
        dto.setUser_alias("alias");
        dto.setUser_name("name");

        System.out.println(dto);

        UserEntity user = DEConverter.toEntity(dto, new UserEntity());
        DetailsEntity details = DEConverter.toEntity(dto, new DetailsEntity());

        Assertions.assertEquals(user.getUser_id(), dto.getUser_id());
        Assertions.assertEquals(user.getUser_alias(), dto.getUser_alias());
        Assertions.assertEquals(user.getUser_details(), dto.getDetails());
        Assertions.assertEquals(details.getDetail_id(), dto.getDetails_id());
        Assertions.assertEquals(details.getDetail_name(), dto.getUser_name());

        System.out.println(user);
        System.out.println(details);
    }

}
