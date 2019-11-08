package by.bsac.core.beans;

import by.bsac.annotations.Dto;
import by.bsac.core.exceptions.NoSupportedEntitiesException;


import java.lang.reflect.Field;
import java.util.Map;

public class BasicDtoEntityConverter<D> implements DtoEntityConverter<D> {

    //Class variables
    private Map<Field, Field> related_fields;

    public BasicDtoEntityConverter(Class<D> dto_clazz) throws NoSupportedEntitiesException {

        //Get supported classes
        Class[] supported = dto_clazz.getAnnotation(Dto.class).value();
        if (supported.length == 0) throw new NoSupportedEntitiesException(dto_clazz);

    }

    @Override
    public <T> T toEntity(D dto, T entity) {
        return null;
    }

    @Override
    public <T> D toDto(T entity, D dto) {
        return null;
    }
}
