package by.bsac.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DtoProperty property annotations used in cases when entity field in DTO class has not same name in related entity class.
 * Specify {@link DtoProperty#entityProperty()} with entity field name which related to DTO field name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DtoProperty {

    /**
     * Name of entity field.
     * @return - {@link String} of entity field name.
     */
    String entityProperty() default "";

}
