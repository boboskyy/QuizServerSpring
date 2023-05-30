package pl.nanocoder.testserver.web.error;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException() {
        super();
    }

    public QuizNotFoundException(String message) {
        super(message);
    }

    public QuizNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
