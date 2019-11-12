package by.bsac.core.beans;

import by.bsac.annotations.Dto;
import by.bsac.core.ConverterUtilz;
import by.bsac.core.exceptions.NoDtoClassException;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.core.exceptions.NoSupportedEntityException;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicDtoEntityConverter<D> implements DtoEntityConverter<D> {

    //Class variables
    private final Map<Class, Map<Field, Field>> related_fields = new HashMap<>();
    private Class<D> dto_class;

    public BasicDtoEntityConverter(Class<D> dto_clazz) throws NoDtoClassException, NoSupportedEntitiesException {

        Dto annotation = dto_clazz.getAnnotation(Dto.class);
        if (annotation == null) throw new NoDtoClassException(dto_clazz);

        //Get supported classes
        Class[] supported_classes = annotation.value();
        if (supported_classes.length == 0) throw new NoSupportedEntitiesException(dto_clazz);

        Arrays.stream(supported_classes).forEach(x -> this.related_fields.put(x, ConverterUtilz.getRelatedFields(x, dto_clazz)));

        //Map fields
        this.dto_class = dto_clazz;
    }

    @Override
    public <T> T toEntity(D dto, T entity) {

        //Check whether entity class is supported by DTO class
        isSupported(entity.getClass());

        Map<Field, Field> related_entity_dto_fields = this.related_fields.get(entity.getClass());

        Map<Field, Field> related_dto_entity_fields =
                related_entity_dto_fields.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        try {
            ConverterUtilz.setRelatedFields(dto, entity, related_dto_entity_fields);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return entity;
    }

    @Override
    public <T> D toDto(T entity, D dto) {

        //Check whether entity class is supported by DTO class
        isSupported(entity.getClass());

        Map<Field, Field> related_entity_dto_fields = this.related_fields.get(entity.getClass());

        try {
            ConverterUtilz.setRelatedFields(entity, dto, related_entity_dto_fields);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return dto;
    }


    public void isSupported(Class entity_class) throws NoSupportedEntityException {

       if (related_fields.keySet().stream().noneMatch(x -> x == entity_class))
            throw new NoSupportedEntityException(entity_class, this.dto_class);
    }
}
