package by.bsac.core.beans;

import by.bsac.annotations.DtoEmbedded;
import by.bsac.core.exceptions.NoDtoClassException;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.core.utils.FieldsUtils;

import java.lang.reflect.Field;
import java.util.*;


public class EmbeddedDtoEntityConverter<D> extends BasicDtoEntityConverter<D> {

    //Class variables

    //Constructor
    public EmbeddedDtoEntityConverter(Class<D> dto_clazz) throws NoDtoClassException, NoSupportedEntitiesException {
        super(dto_clazz);
    }

    public <T> T toEntity(D dto, T entity, Object... embedded_objects) {

        //Check whether entity class is supported by DTO class
        super.isSupported(entity.getClass());

        Set<Field> embedded_fields = FieldsUtils.getAnnotatedFields(DtoEmbedded.class, dto.getClass());

        //Embedded class with it's fields in DTO
        Map<Class, Set<Field>> embedded_with_fields = new HashMap<>();
        for (Field embedded : embedded_fields) {

            Class embedded_class = embedded.getAnnotation(DtoEmbedded.class).value();

            if (!embedded_with_fields.containsKey(embedded_class)) {
                embedded_with_fields.put(embedded_class, new HashSet<>());
                embedded_with_fields.get(embedded_class).add(embedded);
            }else
                embedded_with_fields.get(embedded_class).add(embedded);
        }

        return super.toEntity(dto, entity);

    }



    @Override
    public <T> D toDto(T entity, D dto) {

        //Check whether entity class is supported by DTO class
        super.isSupported(entity.getClass());

        return super.toDto(entity, dto);
    }

}
