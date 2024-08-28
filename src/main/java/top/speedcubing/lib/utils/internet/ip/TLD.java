package top.speedcubing.lib.utils.internet.ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class TLD {

    public static ArrayList<String> getTLDs() {
        try {
            ArrayList<String> arrayList = new ArrayList<>();
            URL url = new URL("https://data.iana.org/TLD/tlds-alpha-by-domain.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#") && !line.trim().isEmpty()) {
                    arrayList.add(line.trim().toLowerCase());
                }
            }
            reader.close();
            return arrayList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
