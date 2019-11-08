package by.bsac.core.beans;

/**
 * Common converter beans. Converted used to convert entity objects to DTO objects and otherwise.
 * @param <D> - DTO class.
 */
public interface DtoEntityConverter<D> {

    <T> T toEntity(D dto, T entity);

    <T> D toDto(T entity, D dto);

}
