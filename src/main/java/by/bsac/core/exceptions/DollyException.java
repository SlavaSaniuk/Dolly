package by.bsac.core.exceptions;

/**
 * Common dolly library exception class for checked exceptions.
 */
public class DollyException extends Exception {

    /**
     * Construct new {@link DollyException} exception which extends {@link Exception} with given exception message.
     * @param msg - Given exception message.
     */
    public DollyException(String msg) {
        super(msg);
    }
}
