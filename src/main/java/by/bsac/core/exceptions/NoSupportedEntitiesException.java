package by.bsac.core.exceptions;

import by.bsac.annotations.Dto;

/**
 * This exception throws when you create implementation of {@link by.bsac.core.beans.DtoEntityConverter}
 * but you DTO class not annotated with {@link by.bsac.annotations.Dto} annotations
 * or DTO annotation has not supported entities classes {@link Dto#value()}.
 */
public class NoSupportedEntitiesException extends DollyException {

    /**
     * Throw new {@link NoSupportedEntitiesException}.
     * @param dto_class - DTO class without annotation value.
     */
    public NoSupportedEntitiesException(Class dto_class) {
        super( String.format("DTO class [%s] has not supported entities " +
                "classes or not annotated with [%s] annotation..",
                dto_class.getCanonicalName(), Dto.class.getCanonicalName()));
    }

}
