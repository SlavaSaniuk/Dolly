import by.bsac.core.DEConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DEConverterTestCase {

    @Test
    void toDto_userEntity_shouldSetDto() {
        User user = new User();
        user.setUser_id(23);
        user.setUser_id_alias("Hello world!");

        UserDto user_dto = DEConverter.toDto(user, new UserDto());

        Assertions.assertEquals(user.getUser_id(), user_dto.getUser_id());
        Assertions.assertEquals(user.getUser_id_alias(), user_dto.getUser_id_alias());
    }

    @Test
    void toEntity_userDto_shouldSetEntity() {
        UserDto user_dto = new UserDto();
        user_dto.setUser_id(24);
        user_dto.setUser_id_alias("Bye!)");
        User user = DEConverter.toEntity(user_dto, new User());
        System.out.println(user.toString());
        Assertions.assertEquals(user_dto.getUser_id(), user.getUser_id());
        Assertions.assertEquals(user.getUser_id_alias(), user_dto.getUser_id_alias());
    }

    @Test
    void toDto_notSupportedEntity_shouldThrowIAE() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> DEConverter.toDto(new DetailsEntity(), new UserDto()));

    }

    @Test
    void isSupport_supportedEntity_shouldReturnTrue() {
        Assertions.assertTrue(DEConverter.isSupported(new User(), new UserDto()));
    }
}
