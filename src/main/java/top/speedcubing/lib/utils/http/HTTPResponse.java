package top.speedcubing.lib.utils.http;

public class HTTPResponse {
    String data;
    int responseCode;

    public HTTPResponse(String data, int responseCode) {
        this.data = data;
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getData() {
        return data;
    }
}
