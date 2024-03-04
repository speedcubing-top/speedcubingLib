package top.speedcubing.lib.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class URIUtils {
    public static String slashAfterDomain(String uri) {
        try {
            URI uri1 = new URI(uri);
            if (uri1.getPath() == null || uri1.getPath().isEmpty())
                return uri + "/";
            return uri;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
