package by.bsac.core.utils;

import by.bsac.collections.SetUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static Set<Field> getAnnotatedFields(Class<? extends Annotation> annotation, Class target) {

        //Check whether annotation class parameter is annotation
        if (!annotation.isAnnotation())
            throw new IllegalArgumentException(String.format("Annotation class parameter [%s] is not annotation.", annotation.getName()));

        return Arrays.stream(target.getDeclaredFields()).filter(x -> x.getAnnotation(annotation) != null).collect(Collectors.toSet());
    }

    public static Field[] getFieldsByType(Class<?> src_clazz, Class<?> field_type) {

        return SetUtils.asSet(src_clazz.getDeclaredFields()).stream().filter(f -> field_type == f.getType()).collect(Collectors.toSet()).toArray(new Field[] {});

    }

}
