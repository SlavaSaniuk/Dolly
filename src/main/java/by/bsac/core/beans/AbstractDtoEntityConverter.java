package by.bsac.core.beans;

import by.bsac.annotations.Dto;
import by.bsac.collections.SetUtils;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Common super class of for all {@link DtoEntityConverter} implementations.
 * @param <D> - DTO class.
 */
public abstract class AbstractDtoEntityConverter<D> implements DtoEntityConverter<D> {

    //Class variables
    @Getter
    protected Class<D> dto_class; //Given DTO class
    @Getter(AccessLevel.PROTECTED)
    private Class[] supported_classes; //Supported entity classes array.

    /**
     * Construct new {@link AbstractDtoEntityConverter} object. Constructor check whether is DTO class properly annotated and gets array of supported entity classes.
     * @param dto_class
     * @throws NoSupportedEntitiesException
     */
    protected AbstractDtoEntityConverter(Class<D> dto_class) throws NoSupportedEntitiesException {

        Dto annotation = dto_class.getAnnotation(Dto.class);
        if (annotation == null || annotation.value().length == 0) throw new NoSupportedEntitiesException(dto_class);

        //Mapping
        this.supported_classes = annotation.value();
        this.dto_class = dto_class;
    }

    /**
     * Check whether given entity class supported by this DTO class.
     * @param entity_class - given entity {@link Class}.
     * @return - 'true' - if DTO class support given entity class.
     */
    public boolean isSupported(Class<?> entity_class) {
        return SetUtils.asSet(supported_classes).contains(entity_class);
    }
}
