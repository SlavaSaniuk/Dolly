package by.bsac.core.beans;

import by.bsac.annotations.Dto;
import by.bsac.collections.SetUtils;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class AbstractDtoEntityConverter<D> implements DtoEntityConverter<D> {

    //Class variables
    @Getter
    protected Class<D> dto_class;
    @Getter(AccessLevel.PROTECTED)
    private Class[] supported_classes;

    public AbstractDtoEntityConverter(Class<D> dto_class) throws NoSupportedEntitiesException {

        Dto annotation = dto_class.getAnnotation(Dto.class);
        if (annotation == null || annotation.value().length == 0) throw new NoSupportedEntitiesException(dto_class);

        this.supported_classes = annotation.value();

        this.dto_class = dto_class;
    }

    public boolean isSupported(Class<?> entity_class) {
        return SetUtils.asSet(supported_classes).contains(entity_class);
    }
}
