package by.bsac.core.beans;

import by.bsac.core.exceptions.NoSupportedEntityException;

/**
 * Common converter beans. Converted used to convert entity objects to DTO objects and otherwise.
 * @param <D> - DTO class.
 */
public interface DtoEntityConverter<D> {

    /**
     * Convert DTO object to entity object (Set fields values
     * from dto object to related entity fields).
     * @param dto - DTO object with fields.
     * @param entity - Entity object to set.
     * @param <T> - Entity type.
     * @return - Entity object.
     * @throws - {@link NoSupportedEntityException} - if given entity object not supported by this DTO class.
     */
    <T> T toEntity(D dto, T entity) throws NoSupportedEntityException;

    /**
     * Convert entity object to DTO object (Set fields values
     * from entity object to related dto fields).
     * @param entity - Entity object with fields.
     * @param dto - DTO object to set.
     * @param <T> - Entity type.
     * @return - DTO object.
     * @throws - {@link NoSupportedEntityException} - if given entity object not supported by this DTO class.
     */
    <T> D toDto(T entity, D dto) throws NoSupportedEntityException;

}
