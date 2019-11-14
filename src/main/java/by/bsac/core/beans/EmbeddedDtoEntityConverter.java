package by.bsac.core.beans;

public interface EmbeddedDtoEntityConverter<D> extends DtoEntityConverter<D> {

    <T> T toEntity(D dto, T entity, Object... emb);
}
