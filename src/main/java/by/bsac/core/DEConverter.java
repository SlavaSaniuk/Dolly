package by.bsac.core;

import by.bsac.annotations.Dto;
import by.bsac.core.utils.FieldsUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

        //Iterate all related fields
        for (Map.Entry<Field, Field> entry :
                relatedFields(ent.getClass().getDeclaredFields(), dto.getClass().getDeclaredFields()).entrySet()) {
            entry.getKey().setAccessible(true);
            entry.getValue().setAccessible(true);
            try {
                entry.getValue().set(dto, entry.getKey().get(ent));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
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

        //Iterate all related fields
        for (Map.Entry<Field, Field> entry :
                relatedFields(ent.getClass().getDeclaredFields(), dto.getClass().getDeclaredFields()).entrySet()) {
            entry.getKey().setAccessible(true);
            entry.getValue().setAccessible(true);
            try {
                entry.getKey().set(ent, entry.getValue().get(dto));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
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

    /**
     * Method return map of related fields ( related means
     * that entity and dto field has a same name and type),
     * where map key is entity field and map value is his related dto field.
     * @param entity_fields - Array of {@link Field} entity fields.
     * @param dto_fields - Array of {@link Field} DTO fields.
     * @return - {@link Map} of related fields.
     */
    private static Map<Field, Field> relatedFields(Field[] entity_fields, Field[] dto_fields) {

        Map<Field, Field> related_fields = new HashMap<>();

        //Iterate all entity fields
        for (Field entity_field : entity_fields) {
            //Iterate all DTO fields in each entity fields iteration round
            for (Field dto_field : dto_fields) {
                //  If entity and dto fields type and name are same
                //  Try to map entity field value to DTO field
                if (FieldsUtils.compareFields(entity_field, dto_field))
                    related_fields.put(entity_field, dto_field);
            }
        }

        return related_fields;
    }

    private static Map<Field, Field> relatedFieldsWithAnnotation(Field[] entity_fields, Field[] dto_fields) {

        final Map<Field, Field> related_fields = new HashMap<>();

        //Iterate all entity fields
        for (Field entity_field : entity_fields) {
            //Iterate all DTO fields in each entity fields iteration round
            for (Field dto_field : dto_fields) {
                //  If entity and dto fields type and DtoProperty annotation value are same
                //  Try to map entity field value to DTO field



                related_fields.put(entity_field, dto_field);


            }
        }

        related_fields.putAll(relatedFields(entity_fields, dto_fields));
        return related_fields;
    }
}
