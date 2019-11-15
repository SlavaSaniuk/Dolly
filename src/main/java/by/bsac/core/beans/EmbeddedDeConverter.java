package by.bsac.core.beans;

import by.bsac.core.exceptions.NoSupportedEntityException;

/**
 * Embedded dto - entity converter extends {@link DtoEntityConverter} and
 * add support to convert DTO to entity with dto embedded fields
 * (annotated with {@link by.bsac.annotations.DtoEmbedded}) class.
 * @param <D> - DTO class.
 */
public interface EmbeddedDeConverter<D> extends DtoEntityConverter<D> {

    /**
     * Convert DTO object to entity. Method also set embedded entity fields
     * delegates in DTO object with
     * {@link by.bsac.annotations.DtoEmbedded} annotation.
     * @param dto - DTO object.
     * @param entity - Entity object.
     * @param emb - Array of embedded entity fields.
     * @param <T> - Entity type.
     * @return - Entity object.
     * @throws - {@link NoSupportedEntityException} - if given entity object not supported by this DTO class.
     */
    <T> T toEntity(D dto, T entity, Object... emb) throws NoSupportedEntityException;
}
