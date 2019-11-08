package utilz;

import by.bsac.core.ConverterUtilz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testentities.*;

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
    void getRelatedFields_entityAndDtoHasRelatedFields_shouldReturnMap() {

        Map<Field, Field> related_fields = ConverterUtilz.getRelatedFields(Book.class, BookDto.class);

        Assertions.assertEquals(2, related_fields.size());

        for (Map.Entry<Field, Field> related_pair : related_fields.entrySet()) {
            System.out.println("Related pair: [" +related_pair.getKey().getName() +"] of type {" +related_pair.getKey().getType().getSimpleName() +
                    "} AND [" +related_pair.getValue().getName() +"] of type {" +related_pair.getValue().getType().getSimpleName() +"}.");
        }

    }

    @Test
    void getRelatedFieldsByAnnotations_entityAndDtoHasAnnotatedRelatedField_shouldReturnMap() {

        Map<Field, Field> related = ConverterUtilz.getRelatedFieldsByAnnotation(Book.class, BookDto.class);

        Assertions.assertEquals(1, related.size());

        for (Map.Entry<Field, Field> related_pair : related.entrySet()) {
            System.out.println("Related pair: [" +related_pair.getKey().getName() +"] of type {" +related_pair.getKey().getType().getSimpleName() +
                    "} AND [" +related_pair.getValue().getName() +"] of type {" +related_pair.getValue().getType().getSimpleName() +"}.");
        }
    }

}
