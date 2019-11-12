package core;

import by.bsac.core.beans.EmbeddedDeConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testentities.dtoembedded.DriverDto;

import java.lang.reflect.Field;
import java.util.Map;

class EmbeddedDeConverterTestCase {

    @Test
    void embeddedDeConverter_embeddedFields_shouldCreateMapOfRelatedFields() {

        EmbeddedDeConverter<DriverDto> converter = new EmbeddedDeConverter<>(DriverDto.class);

        Map<Class, Map<Field, Field>> related_emb_fields = converter.getRelatedEmbeddedFields();
        Assertions.assertEquals(1, related_emb_fields.size());

        related_emb_fields.entrySet().stream()
                .forEach( e -> System.out.println("Embed DTO object of type [" +e.getKey().getName() +"] " +
                        "with related fields [" +e.getValue().toString() +"]"));



    }

}
