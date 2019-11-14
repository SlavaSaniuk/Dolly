package by.bsac.core.beans;

import by.bsac.annotations.DtoEmbedded;
import by.bsac.collections.MapUtils;
import by.bsac.collections.SetUtils;
import by.bsac.core.ConverterUtilz;
import by.bsac.core.exceptions.NoDtoClassException;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.core.exceptions.NoSupportedEntityException;
import by.bsac.core.utils.FieldsUtils;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class EmbeddedDeConverter<D> extends BasicDtoEntityConverter<D> implements EmbeddedDtoEntityConverter<D> {

    //Class variables
    @Getter
    private Map<Class, Map<Field, Field>> related_embedded_fields;

    public EmbeddedDeConverter(Class<D> dto_clazz) throws NoSupportedEntitiesException, NoDtoClassException {
        super(dto_clazz);

        Set<Field> embedded_dto_fields = FieldsUtils.getAnnotatedFields(DtoEmbedded.class, dto_clazz);
        Map<Class, Set<Field>> embedded_dto_objects = new HashMap<>();

        //Update related fields (super class)
        //Remove all fields annotated with @DtoEmbedded from DTO class
        Set<Field> dto_fields = SetUtils.asSet(dto_clazz.getDeclaredFields());
        dto_fields.removeAll(embedded_dto_fields);
        Map<Class, Map<Field, Field>> for_update = Arrays.stream(super.supported_classes).collect(
                Collectors.toMap(x -> x, x -> ConverterUtilz.relatedFields(SetUtils.asSet(x.getDeclaredFields()), dto_fields)));
        super.updateRelatedFields(for_update);

        for (Field emb_field : embedded_dto_fields) {
            Class target_ent = emb_field.getAnnotation(DtoEmbedded.class).value();
            if(embedded_dto_objects.containsKey(target_ent))
                embedded_dto_objects.get(target_ent).add(emb_field);
            else {
                embedded_dto_objects.put(target_ent, new HashSet<>());
                embedded_dto_objects.get(target_ent).add(emb_field);
            }
        }

        Map<Class, Map<Field, Field>> related_emb_fields = new HashMap<>();
        for (Map.Entry<Class, Set<Field>> entry : embedded_dto_objects.entrySet()) {
            related_emb_fields.put(entry.getKey(), ConverterUtilz.relatedFields(Arrays.stream(entry.getKey().getDeclaredFields()).collect(Collectors.toSet()), entry.getValue()));
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
}
