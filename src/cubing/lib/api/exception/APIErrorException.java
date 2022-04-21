package cubing.lib.api.exception;

public class APIErrorException extends RuntimeException {
    public APIErrorException(int response) {
        super(Integer.toString(response));
    }
}
