package cubing.lib.api.exception;

public class APIErrorException extends RuntimeException {
    public APIErrorException(String response) {
        super(response);
    }
}
