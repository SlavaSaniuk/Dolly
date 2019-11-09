package by.bsac.core.exceptions;

public class NoSupportedEntitiesException extends DollyException {

    public NoSupportedEntitiesException(Class dto_class) {
        super( String.format("DTO class [%s] has not supported entities classes.", dto_class.getCanonicalName()));
    }

}
