package top.speedcubing.lib.utils;

import java.io.*;
import java.net.*;

public class HTTPUtils {
    public static String get(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            StringBuilder b = new StringBuilder();
            InputStream in = connection.getInputStream();
            int i;
            while ((i = in.read()) != -1)
                b.append((char) i);
            return b.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
