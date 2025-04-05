package top.speedcubing.lib.api;

import java.sql.SQLException;

public class APIResponseException extends RuntimeException {

    public APIResponseException(String url, int code) {
        super("Failed to call API, URL: " + url + ", Response Code: " + code);
    }
}
