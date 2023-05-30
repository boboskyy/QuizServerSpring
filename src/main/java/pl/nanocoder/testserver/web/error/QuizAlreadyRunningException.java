package pl.nanocoder.testserver.web.error;

public class QuizAlreadyRunningException extends RuntimeException {
    public QuizAlreadyRunningException() {
        super();
    }

    public QuizAlreadyRunningException(String message) {
        super(message);
    }

    public QuizAlreadyRunningException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuizAlreadyRunningException(Throwable cause) {
        super(cause);
    }
}
