package pl.nanocoder.testserver.web.error;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException() {
        super();
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }

    public ScheduleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScheduleNotFoundException(Throwable cause) {
        super(cause);
    }
}
