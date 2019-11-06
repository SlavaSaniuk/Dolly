package utilz;

import by.bsac.core.ConverterUtilz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testentities.UserDto;
import testentities.UserEntity;
import testentities.UserWithDetailsDto;

import java.lang.reflect.Field;
import java.util.Map;

class ConverterUtilzTestCase {

    @Test
    void relatedFieldByName_2relatedFields_shouldReturnMapWith2Entries() {

        Map<Field, Field> related_fields = ConverterUtilz.getRelatedFieldsByName(UserEntity.class, UserDto.class);

        Assertions.assertEquals(2, related_fields.size());

        for (Map.Entry<Field, Field> related_pair : related_fields.entrySet()) {
            System.out.println("Related pair: [" +related_pair.getKey().getName() +"] of type {" +related_pair.getKey().getType().getSimpleName() +
                    "} AND [" +related_pair.getValue().getName() +"] of type {" +related_pair.getValue().getType().getSimpleName() +"}.");
        }
    }

    @Test
    void relatedFieldByAnnotation_oneRelatedFields_shouldReturnMapWith1Entry() {

        Map<Field, Field> related_fields = ConverterUtilz.getRelatedFieldsByAnnotation(UserEntity.class, UserWithDetailsDto.class);

        Assertions.assertEquals(1, related_fields.size());

        for (Map.Entry<Field, Field> related_pair : related_fields.entrySet()) {
            System.out.println("Related pair: [" +related_pair.getKey().getName() +"] of type {" +related_pair.getKey().getType().getSimpleName() +
                    "} AND [" +related_pair.getValue().getName() +"] of type {" +related_pair.getValue().getType().getSimpleName() +"}.");
        }
    }

}
