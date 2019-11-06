package by.bsac.core;

import by.bsac.annotations.Dto;

import java.lang.reflect.Field;
import java.util.*;

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

        Map<Field, Field> related = ConverterUtilz.getRelatedFieldsByAnnotation(ent.getClass(), dto.getClass());
        related.putAll(ConverterUtilz.getRelatedFieldsByName(ent.getClass(), dto.getClass())); ;

        for (Map.Entry<Field, Field> entry : related.entrySet()) {
            entry.getKey().setAccessible(true);
            entry.getValue().setAccessible(true);
            try {
                entry.getValue().set(dto, entry.getKey().get(ent));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

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

        Map<Field, Field> related = ConverterUtilz.getRelatedFieldsByAnnotation(ent.getClass(), dto.getClass());
        related.putAll(ConverterUtilz.getRelatedFieldsByName(ent.getClass(), dto.getClass())); ;

        for (Map.Entry<Field, Field> entry : related.entrySet()) {
            entry.getKey().setAccessible(true);
            entry.getValue().setAccessible(true);
            try {
                entry.getKey().set(ent, entry.getValue().get(dto));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

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
