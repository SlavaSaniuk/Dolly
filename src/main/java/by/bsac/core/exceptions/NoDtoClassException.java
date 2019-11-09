package by.bsac.core.exceptions;

public class NoDtoClassException extends DollyException {

    public NoDtoClassException(Class dto_clazz) {
        super(String.format("Specified [%s] DTO class is not annotated with @Dto annotation", dto_clazz.getCanonicalName()));
    }
}
