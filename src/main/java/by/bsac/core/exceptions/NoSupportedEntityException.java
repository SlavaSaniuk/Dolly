package by.bsac.core.exceptions;

/**
 * Exception throws when user want to convert entity to dto and otherwise,
 * but given entity can not supported by given DTO class.
 */
public class NoSupportedEntityException extends RuntimeException {

    /**
     * Construct new {@link NoSupportedEntityException}.
     * @param entity_class - entity {@link Class} not supported by given DTO class.
     * @param dto_class - DTO {@link Class}.
     */
    public NoSupportedEntityException(Class entity_class, Class dto_class) {
        super(String.format("Required [%s] entity class no supported by this [%s] DTO class.",
                entity_class.getCanonicalName(), dto_class.getCanonicalName()));
    }
}
