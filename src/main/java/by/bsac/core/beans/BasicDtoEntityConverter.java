package by.bsac.core.beans;

import by.bsac.annotations.Dto;
import by.bsac.collections.MapUtils;
import by.bsac.core.ConverterUtilz;
import by.bsac.core.exceptions.NoDtoClassException;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.core.exceptions.NoSupportedEntityException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic implementation of {@link DtoEntityConverter} interface.
 * Class do simple conversion between DTO and Entity objects.
 * Can convert DTO and entities objects without embedded fields.
 * For convert entities with embedded fields use {@link EmbeddedDtoEntityConverter}.
 * @param <D> - DTO type.
 */
public class BasicDtoEntityConverter<D> extends AbstractDtoEntityConverter<D> {

    //Class fields
    //Map of related fields between entity and DTO classes
    private Map<Class, Map<Field, Field>> related_fields = new HashMap<>(); //Key - entity class, value - related fields between entity and DTO classes

    /**
     * Create new {@link BasicDtoEntityConverter} for given DTO class.
     * Constructor get array of classes supported by this DTO, and create map of related fields between DTO class and supported classes.
     * @param dto_clazz - DTO class (Class annotated with {@link Dto} annotation).
     * @throws NoSupportedEntitiesException - if DTO class has not supported entities classes ({@link Dto#value() is not set}.
     */
    //Constructors
    public BasicDtoEntityConverter(Class<D> dto_clazz) throws NoSupportedEntitiesException {

        //AbstractDtoEntityConverter
        super(dto_clazz);

        //Get related fields
        Arrays.stream(super.getSupportedClasses())
                .forEach(x -> this.related_fields.put(x, ConverterUtilz.getRelatedFields(x, dto_clazz)));

    }

    @Override
    public <T> T toEntity(D dto, T entity) {

        //Check whether entity class is supported by DTO class
        if (!super.isSupported(entity.getClass()))
            throw new NoSupportedEntityException(entity.getClass(), super.dto_class);

        //Invert map
        Map<Field, Field> related_dto_entity_fields =
                MapUtils.invertMap(this.related_fields.get(entity.getClass()));

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
        if (!super.isSupported(entity.getClass()))
            throw new NoSupportedEntityException(entity.getClass(), super.dto_class);

        Map<Field, Field> related_entity_dto_fields = this.related_fields.get(entity.getClass());

        try {
            ConverterUtilz.setRelatedFields(entity, dto, related_entity_dto_fields);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return dto;
    }

    /**
     * Method update this map of related fields with new given map.
     * @param new_related - new {@link Map} of related entity - DTO fields.
     */
    protected void updateRelatedFields(Map<Class, Map<Field, Field>> new_related) {
        this.related_fields = new_related;
    }

}
