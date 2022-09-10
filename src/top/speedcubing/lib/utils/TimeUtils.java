package top.speedcubing.lib.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static String unixToRealTime(long t, String format, TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                return DateTimeFormatter.ofPattern(format).format(LocalDateTime.ofInstant(Instant.ofEpochSecond(t), ZoneId.systemDefault()));
        }
        return null;
    }

    public static String unixToTime(long t, String format, TimeUnit timeUnit) {
        int i;
        String ms = "null";
        switch (timeUnit) {
            case SECONDS:
                i = 1;
                break;
            case MILLISECONDS:
                i = 1000;
                ms = Long.toString(t % 1000);
                break;
            default:
                return null;
        }
        String d = Long.toString(t / 86400 / i);
        String h = String.format("%02d", (t / 3600 / i) % 24);
        String m = String.format("%02d", (t / 60 / i) % 60);
        String s = String.format("%02d", (t / i) % 60);
        return format.replace("%d%", d).replace("%h%", h).replace("%m%", m).replace("%s%", s).replace("%ms%", ms);
    }
}
