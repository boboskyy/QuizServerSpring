package pl.nanocoder.testserver.web.error;

public class QGInvalidModulesCountException extends RuntimeException {
    public QGInvalidModulesCountException() {
        super();
    }

    public QGInvalidModulesCountException(String message) {
        super(message);
    }

    public QGInvalidModulesCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public QGInvalidModulesCountException(Throwable cause) {
        super(cause);
    }
}
