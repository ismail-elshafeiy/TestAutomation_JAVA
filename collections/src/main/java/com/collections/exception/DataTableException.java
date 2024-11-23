
package com.collections.exception;

public class DataTableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataTableException() {
        super();
    }

    public DataTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataTableException(String message) {
        super(message);
    }

    public DataTableException(Throwable cause) {
        super(cause);
    }
}
