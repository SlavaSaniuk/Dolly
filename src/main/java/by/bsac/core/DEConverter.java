package by.bsac.core;

import by.bsac.annotations.Dto;
import by.bsac.core.utils.FieldsUtils;

import java.lang.reflect.Field;

/**
 * DTO Entity Converter class can convert entity to DTO objects and otherwise.
 * Note: DTO object mast support entity objects. Use {@link Dto} annotation
 * to set which entity types this DTO object can support.
 */
public class DEConverter {

    //Class variables
    private static final String NOT_SUPPORTED_MSG = "This [%s] DTO class not support required [%s] entity class.";

    /**
     * Extract entity fields values and set it to DTO object.
     * @param ent - required entity object.
     * @param dto - DTO object to set.
     * @param <D> - DTO class.
     * @param <E> - Entity class.
     * @return - DTO object with fields values from required entity object.
     */
    public static  <D, E> D toDto(E ent, D dto) {

        //Check whether DTO class supported required entity class
        if (!isSupported(ent, dto))
            throw new IllegalArgumentException(String.format(NOT_SUPPORTED_MSG,
                    dto.getClass().getCanonicalName(), ent.getClass().getCanonicalName()));

        //Try to map entity fields to DTO fields
        final Field[] entity_fields = ent.getClass().getDeclaredFields();
        final Field[] dto_fields = dto.getClass().getDeclaredFields();

        //Iterate all entity fields
        for (Field entity_field : entity_fields) {

            //Iterate all DTO fields in each entity fields iteration round
            for (Field dto_field : dto_fields) {

                //  If entity and dto fields type and name are same
                //  Try to map entity field value to DTO field
                if (FieldsUtils.compareFields(entity_field, dto_field)) {
                    entity_field.setAccessible(true);
                    dto_field.setAccessible(true);
                    try {
                        dto_field.set(dto, entity_field.get(ent));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        //Return DTO object with field established from entity object
       return dto;
    }

    /**
     * Extract DTO fields values and set it to entity object.
     * @param dto - DTO object with defined values.
     * @param ent - required entity object to set.
     * @param <D> - DTO class.
     * @param <E> - Entity class.
     * @return - Entity object with fields values from specified DTO object.
     */
    public static <D, E> E toEntity(D dto, E ent) {

        //Check whether DTO class supported required entity class
        if (!isSupported(ent, dto))
            throw new IllegalArgumentException(String.format(NOT_SUPPORTED_MSG,
                    dto.getClass().getCanonicalName(), ent.getClass().getCanonicalName()));

        //Try to map entity fields to DTO fields
        final Field[] dto_fields = dto.getClass().getDeclaredFields();
        final Field[] entity_fields = ent.getClass().getDeclaredFields();

        //Iterate all entity fields
        for (Field entity_field : entity_fields) {

            //Iterate all DTO fields in each entity fields iteration round
            for (Field dto_field : dto_fields) {

                //  If entity and dto fields type and name are same
                //  Try to map entity field value to DTO field
                if (FieldsUtils.compareFields(entity_field, dto_field)) {
                    entity_field.setAccessible(true);
                    dto_field.setAccessible(true);
                    try {
                        entity_field.set(ent, dto_field.get(dto));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        //Return entity object with field established from DTO object
        return ent;
    }

    /**
     * Check whether DTO object is support required entity conversion.
     * @param ent - required entity object.
     * @param dto - DTO object.
     * @param <D> - DTO class.
     * @param <E> - Entity class.
     * @return - {@link Boolean#TRUE} if DTO object support required entity conversion.
     */
    public static <D, E> boolean isSupported(E ent, D dto) {

        //Get supported entity classes
        Dto annotation = dto.getClass().getAnnotation(Dto.class);
        Class[] supported_entities = annotation.value();

        for (Class supported : supported_entities)
            if (supported == ent.getClass()) return true;

         return false;
    }
}
