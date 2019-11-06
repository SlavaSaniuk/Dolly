package by.bsac.core.utils;

import java.lang.reflect.Field;

/**
 * Additional {@link Field} reflection methods.
 */
public class FieldsUtils {

    /**
     * Compare two fields by it's name and type.
     * Note: Do not compare fields values.
     * @param f1 - {@link Field} first field to compare.
     * @param f2 - {@link Field} second field to compare.
     * @return - {@link Boolean#TRUE} - if fields name and type are same.
     */
    public static boolean equalsFields(Field f1, Field f2) {
        if (f1.getName().equals(f2.getName())) {
            return f1.getType() == f2.getType();
        }else {
            return false;
        }
    }

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
