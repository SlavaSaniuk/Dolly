package by.bsac.core.beans;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoEmbedded;
import by.bsac.collections.MapUtils;
import by.bsac.collections.SetUtils;
import by.bsac.core.ConverterUtilz;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.core.utils.FieldsUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link EmbeddedDeConverter}.
 * Class give support for conversion between DTO and entity object
 * with embedded entity fields.
 * @param <D> - DTO class.
 */
public class EmbeddedDtoEntityConverter<D> extends BasicDtoEntityConverter<D> implements EmbeddedDeConverter<D> {

    //Class variables
    //Map of related embedded fields between entities classes and given DTO class.
    private Map<Class<?>, Map<Field, Field>> related_embedded_fields;

    /**
     * Create new {@link EmbeddedDtoEntityConverter} object for given DTO class.
     * @param dto_clazz - DTO {@link Class}.
     * @throws NoSupportedEntitiesException - if given DTO class not annotated with {@link by.bsac.annotations.Dto} annotation, or has not supported classes {@link Dto#value() is null or empty}.
     */
    public EmbeddedDtoEntityConverter(Class<D> dto_clazz) throws NoSupportedEntitiesException {

        super(dto_clazz);

        //Update related fields (super class)
        //Remove all fields annotated with @DtoEmbedded from DTO class
        Set<Field> embedded_dto_fields = FieldsUtils.getAnnotatedFields(DtoEmbedded.class, dto_clazz);

        Set<Field> dto_fields = SetUtils.asSet(dto_clazz.getDeclaredFields());
        dto_fields.removeAll(embedded_dto_fields);
        //Update related fields
        Map<Class, Map<Field, Field>> for_update = Arrays.stream(super.getSupportedClasses()).collect(
                Collectors.toMap(x -> x, x -> ConverterUtilz.relatedFields(SetUtils.asSet(x.getDeclaredFields()), dto_fields)));
        super.updateRelatedFields(for_update);

        //Sort embedded fields by embedded entity objects
        Map<Class, Set<Field>> embedded_dto_objects = new HashMap<>();
        for (Field emb_field : embedded_dto_fields) {
            Class target_ent = emb_field.getAnnotation(DtoEmbedded.class).value();
            if(embedded_dto_objects.containsKey(target_ent))
                embedded_dto_objects.get(target_ent).add(emb_field);
            else {
                embedded_dto_objects.put(target_ent, new HashSet<>());
                embedded_dto_objects.get(target_ent).add(emb_field);
            }
        }

        Map<Class<?>, Map<Field, Field>> related_emb_fields = new HashMap<>();
        for (Map.Entry<Class, Set<Field>> entry : embedded_dto_objects.entrySet()) {
            related_emb_fields.put(entry.getKey(), ConverterUtilz.relatedFields(SetUtils.asSet(entry.getKey().getDeclaredFields()), entry.getValue()));
        }

        this.related_embedded_fields = related_emb_fields;
    }

    @Override
    public <T> T toEntity(D dto, T entity, Object... emb) {

        final T ent =  super.toEntity(dto, entity);

        Arrays.stream(emb).forEach( em -> {

            if (this.related_embedded_fields.containsKey(em.getClass())) {

                Map<Field, Field> inv = MapUtils.invertMap(this.related_embedded_fields.get(em.getClass()));

                try {
                    ConverterUtilz.setRelatedFields(dto, em, inv);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                Field emb_field = FieldsUtils.getFieldsByType(ent.getClass(), em.getClass())[0];

                emb_field.setAccessible(true);
                try {
                    emb_field.set(ent, em);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        });

        return ent;
    }

    @Override
    public <T> D toDto(T entity, D dto) {

        dto = super.toDto(entity, dto);

        Set<Object> entity_emb_objs = new HashSet<>();

        SetUtils.asSet(entity.getClass().getDeclaredFields()).stream()
                .filter(f -> this.related_embedded_fields.containsKey(f.getType())).forEach(x -> {
                    x.setAccessible(true);
                    try {
                        entity_emb_objs.add(x.get(entity));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
        });

        for (Object emb : entity_emb_objs) {

            Map<Field, Field> related_emb = this.related_embedded_fields.get(emb.getClass());
            try {
                ConverterUtilz.setRelatedFields(emb, dto, related_emb);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return dto;
    }

    protected void updateEmbeddedFields(Map<Class<?>, Map<Field, Field>> new_embedded_fields) {
        this.related_embedded_fields = new_embedded_fields;
    }
}
