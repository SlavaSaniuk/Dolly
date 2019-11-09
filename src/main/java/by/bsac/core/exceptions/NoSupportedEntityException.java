package by.bsac.core.exceptions;

public class NoSupportedEntityException extends RuntimeException {

    public NoSupportedEntityException(Class entity_class, Class dto_class) {
        super(String.format("Required [%s] entity class no supported by this [%s] DTO class.",
                entity_class.getCanonicalName(), dto_class.getCanonicalName()));
    }
}
