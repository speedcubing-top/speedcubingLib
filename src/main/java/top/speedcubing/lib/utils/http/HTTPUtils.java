package top.speedcubing.lib.utils.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPUtils {
    public static HTTPResponse get(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        StringBuilder b = new StringBuilder();
        InputStream in = connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream();
        int i;
        while ((i = in.read()) != -1)
            b.append((char) i);
        return new HTTPResponse(b.toString(), connection.getResponseCode());
    }
}
