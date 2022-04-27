//package cubing.api.hypixel;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import cubing.api.exception.APIErrorException;
//
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.UUID;
//
//public class HypixelAPI {
//    public static JsonObject getPlayer(UUID uuid, String key) {
//        try {
//            URL url = new URL("https://api.hypixel.net/player?key=" + key + "&uuid=" + uuid);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            switch (connection.getResponseCode()) {
//                case 403:
//                    throw new APIErrorException("403");
//                default:
//                    return new JsonParser().parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
