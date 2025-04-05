package top.speedcubing.lib.utils.http;

public class HTTPResponse {
    private final String url;
    private final String data;
    private final int responseCode;

    public HTTPResponse(String url, String data, int responseCode) {
        this.url = url;
        this.data = data;
        this.responseCode = responseCode;
    }

    public String getUrl() {
        return url;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getData() {
        return data;
    }
}
