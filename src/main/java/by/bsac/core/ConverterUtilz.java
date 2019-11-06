package by.bsac.core;

import by.bsac.annotations.DtoProperty;
import by.bsac.core.utils.FieldsUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ConverterUtilz {

    /**
     * Methods return map of fields entries where entry field key has a same
     * type and name as entry field value.
     * @param e_class - {@link Class} Entity class with fields.
     * @param d_class - {@link Class} DTO class with fields.
     * @return - {@link Map} of related fields, where key is Entity field and value it's related DTO field.
     */
    public static Map<Field, Field> getRelatedFieldsByName(Class e_class, Class d_class) {

        final Field[] e_fields = e_class.getDeclaredFields();
        final Field[] d_fields = d_class.getDeclaredFields();

        final Map<Field, Field> related_fields = new HashMap<>();

        for (Field e_field : e_fields) {
            for (Field d_field : d_fields) {

                //If fields has a same name and type - it's related
                if (FieldsUtils.equalsFields(e_field, d_field))
                    related_fields.put(e_field, d_field);

            }
        }

        return related_fields;
    }

    /**
     * Methods return map of fields entries where entry field key has a same
     * name as entry field value DtoProperty annotation value .
     * @param e_class - {@link Class} Entity class with fields.
     * @param d_class - {@link Class} DTO class with fields.
     * @return - {@link Map} of related fields, where key is Entity field and value it's related DTO field.
     */
    public static Map<Field, Field> getRelatedFieldsByAnnotation(Class e_class, Class d_class) {

        final Field[] e_fields = e_class.getDeclaredFields();
        final Field[] d_fields = d_class.getDeclaredFields();

        final Map<Field, Field> related_fields = new HashMap<>();

        for (Field e_field : e_fields) {
            for (Field d_field : d_fields) {

                DtoProperty annotation = d_field.getAnnotation(DtoProperty.class);
                if (annotation == null) continue;
                String entity_property = annotation.entityProperty();
                if (entity_property.isEmpty()) continue;

                if (FieldsUtils.compareName(e_field, entity_property) && FieldsUtils.equalsByType(e_field, d_field))
                    related_fields.put(e_field, d_field);
            }
        }

        return related_fields;
    }

}
