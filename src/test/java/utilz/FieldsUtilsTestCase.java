package utilz;

import by.bsac.annotations.DtoEmbedded;
import by.bsac.core.utils.FieldsUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testentities.dtoembedded.DriverDto;

import java.lang.reflect.Field;
import java.util.Set;

class FieldsUtilsTestCase {

    @Test
    void getAnnotatedFields_classHasAnnotatedFields_shouldReturnThisFields() {

        Set<Field> annotated = FieldsUtils.getAnnotatedFields(DtoEmbedded.class, DriverDto.class);

        Assertions.assertEquals(1, annotated.size());
        annotated.forEach(x -> System.out.println(String.format("Fields [%s] of type [%s];", x.getName(), x.getType())));
    }

}
