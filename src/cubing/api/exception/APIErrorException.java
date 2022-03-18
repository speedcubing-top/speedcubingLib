package cubing.api.exception;

public class APIErrorException extends RuntimeException {
    public APIErrorException(String response) {
        super(response);
    }
}
