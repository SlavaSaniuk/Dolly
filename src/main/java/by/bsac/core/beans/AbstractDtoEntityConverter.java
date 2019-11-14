package by.bsac.core.beans;

import by.bsac.annotations.Dto;
import by.bsac.core.exceptions.NoSupportedEntitiesException;

import java.util.Arrays;


public abstract class AbstractDtoEntityConverter<D> implements DtoEntityConverter<D> {

    //Class variables
    protected Class<D> dto_class;

    public AbstractDtoEntityConverter(Class<D> dto_class) throws NoSupportedEntitiesException {

        Dto annotation = dto_class.getAnnotation(Dto.class);
        if (annotation == null || annotation.value().length == 0) throw new NoSupportedEntitiesException(dto_class);

        this.dto_class = dto_class;
    }

    public boolean isSupported(Class<?> entity_class) {
    }
}
