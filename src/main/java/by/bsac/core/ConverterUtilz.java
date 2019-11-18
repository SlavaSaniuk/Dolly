package by.bsac.core;

import by.bsac.annotations.DtoProperty;
import by.bsac.core.utils.FieldsUtils;

import java.lang.reflect.Field;
import java.util.*;

public class ConverterUtilz {

    /**
     * Methods return map of fields entries where entry field key has a same
     * type and name as entry field value.
     * @param e_class - {@link Class} Entity class with fields.
     * @param d_class - {@link Class} DTO class with fields.
     * @return - {@link Map} of related fields, where key is Entity field and value it's related DTO field.
     */
    public static Map<Field, Field> getRelatedFieldsByName(Class e_class, Class d_class) {

        Set<Field> entity_fields = new HashSet<>(Arrays.asList(e_class.getDeclaredFields()));
        Set<Field> dto_fields = new HashSet<>(Arrays.asList(d_class.getDeclaredFields()));

        return relatedByName(entity_fields, dto_fields);
    }

    private static Map<Field, Field> relatedByName(Set<Field> e_fields, Set<Field> d_fields) {

        final Map<Field, Field> related = new HashMap<>();

        for (Field ef : e_fields) {
            for (Field df : d_fields) {
                if (FieldsUtils.equalsByName(ef, df) && FieldsUtils.equalsByType(ef, df)) {
                    related.put(ef, df);
                    break;
                }
            }
        }

        return related;
    }

    /**
     * Methods return map of fields entries where entry field key has a same
     * name as entry field value DtoProperty annotation value .
     * @param e_class - {@link Class} Entity class with fields.
     * @param d_class - {@link Class} DTO class with fields.
     * @return - {@link Map} of related fields, where key is Entity field and value it's related DTO field.
     */
    public static Map<Field, Field> getRelatedFieldsByAnnotation(Class e_class, Class d_class) {

        Set<Field> entity_fields = new HashSet<>(Arrays.asList(e_class.getDeclaredFields()));
        Set<Field> dto_fields = new HashSet<>(Arrays.asList(d_class.getDeclaredFields()));

        return relatedByAnnotations(entity_fields, dto_fields);
    }

    private static Map<Field, Field> relatedByAnnotations(Set<Field> e_fields, Set<Field> d_fields) {

        Map<Field, Field> related = new HashMap<>();

        for (Field df : d_fields) {

            DtoProperty annotation = df.getAnnotation(DtoProperty.class);
            if (annotation == null) continue;

            String entity_property = annotation.entityProperty();
            if (entity_property.isEmpty()) continue;

            for (Field ef : e_fields) {
                if (FieldsUtils.compareName(ef, entity_property) && FieldsUtils.equalsByType(ef, df))
                    related.put(ef, df);
            }
        }

        return related;
    }

    public static Map<Field, Field> getRelatedFields(Class e_class, Class d_class) {


        Set<Field> entity_fields = new HashSet<>(Arrays.asList(e_class.getDeclaredFields()));
        Set<Field> dto_fields = new HashSet<>(Arrays.asList(d_class.getDeclaredFields()));

        return relatedFields(entity_fields, dto_fields);
    }

    public static Map<Field, Field> relatedFields(Set<Field> entity_fields, Set<Field> dto_fields) {

        //related by annotations
        Map<Field, Field> related = relatedByAnnotations(entity_fields, dto_fields);
        entity_fields.removeAll(related.keySet());
        dto_fields.removeAll(related.values());

        //related by annotations and name
        related.putAll(relatedByName(entity_fields, dto_fields));

        return related;

    }

    /**
     * Set related fields values from one object to other object.
     * Note: {@link Map} of related fields must contains fields of "from" object as map keys,
     * and fields of "to" object as map values.
     * @param from - {@link Object} from gets values.
     * @param to - {@link Object} to sets values.
     * @param related_fields - {@link Map} of related fields. Map must contains fields of "from" object as map keys,
     * and fields of "to" object as map values.
     * @throws IllegalAccessException - {@link IllegalAccessException}.
     */
    public static void setRelatedFields(Object from, Object to, Map<Field, Field> related_fields) throws IllegalAccessException {

        for (Map.Entry<Field, Field> entry : related_fields.entrySet()) {
            entry.getKey().setAccessible(true);
            entry.getValue().setAccessible(true);
            entry.getValue().set(to, entry.getKey().get(from));
            setField(entry.getKey(), entry.getValue(), from, to);
        }

    }

    private static void setField(Field f_from, Field f_to, Object o_from, Object o_to) throws IllegalAccessException {
        f_from.setAccessible(true);
        f_to.setAccessible(true);
        f_to.set(o_to, f_from.get(o_from));
    }

}
