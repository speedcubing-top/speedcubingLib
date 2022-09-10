package top.speedcubing.lib.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeFormatter {
    String d;
    String iH;
    String h;
    String m;
    String s;
    String ms;
    long t;
    int i;

    public TimeFormatter(long t, TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                i = 1;
                break;
            case MILLISECONDS:
                i = 1000;
                ms = Long.toString(t % 1000);
                break;
        }
        this.t = t;
        long a = (t / i);
        s = String.format("%02d", a % 60);
        a = a / 60;
        m = String.format("%02d", a % 60);
        a = a / 60;
        h = String.format("%02d", a % 24);
        iH = Long.toString(a);
        a = a / 24;
        d = Long.toString(a);
    }

    public String format(String format) {
        return format
                .replace("%d%", d)
                .replace("%H%", iH)
                .replace("%h%", h)
                .replace("%m%", m)
                .replace("%s%", s)
                .replace("%ms%", ms);
    }

    public static String unixToRealTime(long t, String format, TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                return DateTimeFormatter.ofPattern(format).format(LocalDateTime.ofInstant(Instant.ofEpochSecond(t), ZoneId.systemDefault()));
        }
        return null;
    }
}
