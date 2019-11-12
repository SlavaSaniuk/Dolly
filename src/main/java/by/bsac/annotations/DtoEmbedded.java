package by.bsac.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DtoEmbedded annotation used in cases
 * when target DTO class has an fields which embedded
 * as one object fields in supported entity class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DtoEmbedded {

    /**
     * Target class of embedded entity field.
     * @return - {@link Class} of embedded entity fields.
     */
    Class value();

}
