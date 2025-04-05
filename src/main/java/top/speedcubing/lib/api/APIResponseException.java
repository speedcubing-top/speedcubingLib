package top.speedcubing.lib.api;

public class APIResponseException extends RuntimeException {

    private final String url;
    private final int responseCode;

    public APIResponseException(String url, int responseCode) {
        super("Failed to call API, URL: " + url + ", ResponseCode: " + responseCode);
        this.url = url;
        this.responseCode = responseCode;
    }

    public String getURL() {
        return url;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
