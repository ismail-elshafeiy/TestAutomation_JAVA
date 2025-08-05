
package javaPractise.advancedTopics.collections.exception;

public class DataStreamException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataStreamException() {
        super();
    }

    public DataStreamException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataStreamException(String message) {
        super(message);
    }

    public DataStreamException(Throwable cause) {
        super(cause);
    }
}
