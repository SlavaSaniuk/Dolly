package core;

import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.core.exceptions.NoDtoClassException;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import testentities.dtoembedded.Driver;
import testentities.dtoembedded.DriverDto;
import testentities.dtoembedded.DriverName;

import java.lang.reflect.Field;
import java.util.Map;

class EmbeddedDeConverterTestCase {

    @Test
    @Disabled
    void embeddedDeConverter_embeddedFields_shouldCreateMapOfRelatedFields() throws NoSupportedEntitiesException, NoDtoClassException {

        EmbeddedDeConverter<DriverDto> converter = new EmbeddedDeConverter<>(DriverDto.class);

        Map<Class, Map<Field, Field>> related_emb_fields = converter.getRelatedEmbeddedFields();
        Assertions.assertEquals(1, related_emb_fields.size());

        related_emb_fields.entrySet().stream()
                .forEach( e -> System.out.println("Embed DTO object of type [" +e.getKey().getName() +"] " +
                        "with related fields [" +e.getValue().toString() +"]"));

    }

    @Test
    void toEntity_dtoWithOneEmbeddedField_shouldReturnEntity() throws NoSupportedEntitiesException, NoDtoClassException {

        EmbeddedDeConverter<DriverDto> converter = new EmbeddedDeConverter<>(DriverDto.class);

        DriverDto dto = new DriverDto();
        dto.setId(24);
        dto.setFName("FNAME");
        dto.setSurname("SURNAME");

        Driver ent = converter.toEntity(dto, new Driver(), new DriverName());

        Assertions.assertNotNull(ent);
        Assertions.assertEquals(dto.getFName(), ent.getDriverName().getFName());
        Assertions.assertEquals(dto.getSurname(), ent.getDriverName().getLName());

        System.out.println(dto.toString());
        System.out.println(ent.toString());
    }

    @Test
    void toDto_entityWithEmbeddedField_shouldReturnEntity() throws NoSupportedEntitiesException, NoDtoClassException {

        EmbeddedDeConverter<DriverDto> converter = new EmbeddedDeConverter<>(DriverDto.class);

        Driver ent = new Driver();
        ent.setDriverExperience(8);
        ent.setDriverId(26);
        ent.setDriverName(new DriverName("FNAME", "LNAME"));

        DriverDto dto = converter.toDto(ent, new DriverDto());

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(ent.getDriverId(), dto.getId());
        Assertions.assertEquals(ent.getDriverName().getFName(), dto.getFName());
        Assertions.assertEquals(ent.getDriverName().getLName(), dto.getSurname());

    }

}
