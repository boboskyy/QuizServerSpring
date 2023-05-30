package pl.nanocoder.testserver.web.error;

public class QuizExpiredException extends RuntimeException {
    public QuizExpiredException() {
        super();
    }

    public QuizExpiredException(String message) {
        super(message);
    }

    public QuizExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuizExpiredException(Throwable cause) {
        super(cause);
    }
}
