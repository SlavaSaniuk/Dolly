package by.bsac.core.beans;

import by.bsac.annotations.DtoEmbedded;
import by.bsac.core.ConverterUtilz;
import by.bsac.core.utils.FieldsUtils;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class EmbeddedDeConverter<D> implements DtoEntityConverter<D> {

    //Class variables
    private Map<Class, Map<Field, Field>> related_embedded_fields;

    public EmbeddedDeConverter(Class<D> dto_clazz) {

        Set<Field> embedded_dto_fields = FieldsUtils.getAnnotatedFields(DtoEmbedded.class, dto_clazz);
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

        Map<Class, Map<Field, Field>> related_emb_fields = new HashMap<>();
        for (Map.Entry<Class, Set<Field>> entry : embedded_dto_objects.entrySet()) {
            related_emb_fields.put(entry.getKey(), ConverterUtilz.relatedFields(Arrays.stream(entry.getKey().getDeclaredFields()).collect(Collectors.toSet()), entry.getValue()));
        }

        this.related_embedded_fields = related_emb_fields;

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
