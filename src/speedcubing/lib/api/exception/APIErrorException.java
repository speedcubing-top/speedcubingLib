package speedcubing.lib.api.exception;

public class APIErrorException extends RuntimeException {

    public APIErrorException() {
        super();
    }

    public APIErrorException(int response) {
        super(Integer.toString(response));
    }
}
