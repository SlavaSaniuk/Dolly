package by.bsac.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Main Dolly library annotation which indicate Data Transfer Object (DTO) class.
 * You need to set {@link Dto#value()} classes value to indicate which entity classes
 * this DTO class can support.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Dto {

    /**
     * Specify entity classes which this DTO class support.
     * For example If BookWithAuthorDto dto class consist
     * of Book and Author entity class,
     * you must to set Book and Author classes as supported classes
     * by this annotation.
     * @return - {@link Class} array of supported entity classes.
     */
    Class[] value();
}
