package by.bsac.core.utils;

import java.lang.reflect.Field;

/**
 * Additional {@link Field} reflection methods.
 */
public class FieldsUtils {

    public static boolean equalsByType(Field f1, Field f2) {
        return f1.getType() == f2.getType();
    }

    public static boolean equalsByName(Field f1, Field f2) {
        return FieldsUtils.compareName(f1, f2.getName());
    }

    public static boolean compareName(Field field, String name) {
        return field.getName().equals(name);
    }


}
