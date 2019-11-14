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

/**
 * Basic implementation of {@link DtoEntityConverter} interface.
 * Class do simple conversion between DTO and Entity objects.
 * Can convert DTO and entities objects without embedded fields.
 * For convert entities with embedded fields use {@link EmbeddedDeConverter}.
 * @param <D> - DTO type.
 */
@SuppressWarnings("WeakerAccess")
public class BasicDtoEntityConverter<D> implements DtoEntityConverter<D> {

    //Class variables
    private Class<D> dto_class; //DTO class

    /**
     * Array of classes that this DTO class can support.
     */
    protected Class[] supported_classes; //Array of supported entity classes

    //Map of related fields between entity and DTO classes
    private Map<Class, Map<Field, Field>> related_fields = new HashMap<>(); //Key - entity class, value - related fields between entity and DTO classes

    /**
     * Create new {@link BasicDtoEntityConverter} for given DTO class.
     * Constructor get array of classes supported by this DTO, and create map of related fields between DTO class and supported classes.
     * @param dto_clazz - DTO class (Class annotated with {@link Dto} annotation).
     * @throws NoDtoClassException - if DTO class not annotated with {@link Dto} annotation.
     * @throws NoSupportedEntitiesException - if DTO class has not supported entities classes ({@link Dto#value() is not set}.
     */
    //Constructors
    public BasicDtoEntityConverter(Class<D> dto_clazz) throws NoDtoClassException, NoSupportedEntitiesException {

        //Exception thrown if given DTO class not annotated with @Dto annotation
        Dto annotation = dto_clazz.getAnnotation(Dto.class);
        if (annotation == null) throw new NoDtoClassException(dto_clazz);

        //Get supported classes
        Class[] supported_classes = annotation.value();
        if (supported_classes.length == 0) throw new NoSupportedEntitiesException(dto_clazz);
        this.supported_classes = supported_classes;

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

    private void isSupported(Class entity_class) throws NoSupportedEntityException {

       if (related_fields.keySet().stream().noneMatch(x -> x == entity_class))
            throw new NoSupportedEntityException(entity_class, this.dto_class);
    }

    /**
     * Method update this converter map of related fields with new map.
     * @param new_related - new {@link Map} of related entity - DTO fields.
     */
    protected void updateRelatedFields(Map<Class, Map<Field, Field>> new_related) {
        this.related_fields = new_related;
    }

}
