package top.speedcubing.lib.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeFormatter {
    public final long w;
    public final long D;
    public final long d;
    public final long H;
    public final long h;
    public final long m;
    public final long s;
    public final long ms;
    String format = "";

    public TimeFormatter(long t, TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                ms = 0;
                break;
            case MILLISECONDS:
                ms = t % 1000;
                t = t / 1000;
                break;
            default:
                ms = 0;
                break;
        }
        s = t % 60;
        t = t / 60;
        m = t % 60;
        t = t / 60;
        h = t % 24;
        H = t;
        t = t / 24;
        d = t % 7;
        D = t;
        t = t / 7;
        w = t;
    }

    public TimeFormatter format(String format, boolean deleteIfEmpty) {
        if (deleteIfEmpty && this.format.equals("")) {
            if (w == 0 && (format.contains("%w%") || format.contains("%?w%")))
                return this;
            if (D == 0 && format.contains("%D%"))
                return this;
            if (d == 0 && format.contains("%d%") || format.contains("%?d%"))
                return this;
            if (H == 0 && format.contains("%H%"))
                return this;
            if (h == 0 && format.contains("%h%") || format.contains("%?h%"))
                return this;
            if (m == 0 && (format.contains("%m%") || format.contains("%?m%")))
                return this;
            if (s == 0 && (format.contains("%s%") || format.contains("%?s%")))
                return this;
            if (ms == 0 && (format.contains("%ms%") || format.contains("%?ms%")))
                return this;
        }
        format = format
                .replace("%w%", Long.toString(w))
                .replace("%d%", String.format("%03d", d)).replace("%?d%", Long.toString(d)).replace("%D%", Long.toString(D))
                .replace("%h%", String.format("%02d", h)).replace("%?h%", Long.toString(h)).replace("%H%", Long.toString(H))
                .replace("%m%", String.format("%02d", m)).replace("%?m%", Long.toString(m))
                .replace("%s%", String.format("%02d", s)).replace("%?s%", Long.toString(s))
                .replace("%ms%", String.format("%03d", ms)).replace("%?ms%", Long.toString(ms));
        this.format += format;
        return this;
    }

    public String toString() {
        return format;
    }

    public static String unixToRealTime(long t, String format, TimeUnit timeUnit) {
        return unixToRealTime(t, DateTimeFormatter.ofPattern(format), timeUnit);
    }

    public static String unixToRealTime(long t, DateTimeFormatter formatter, TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                return unixToRealTime(Instant.ofEpochSecond(t), formatter);
            case MILLISECONDS:
                return unixToRealTime(Instant.ofEpochMilli(t), formatter);
        }
        return null;
    }

    public static String unixToRealTime(Instant instant, DateTimeFormatter formatter) {
        return formatter.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
    }

    public static String unixToRealTime(String format, OffsetDateTime offsetDateTime) {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.ofInstant(offsetDateTime.toInstant(), ZoneId.systemDefault()));
    }

    public static String unixToRealTime(DateTimeFormatter formatter, OffsetDateTime offsetDateTime) {
        return formatter.format(LocalDateTime.ofInstant(offsetDateTime.toInstant(), ZoneId.systemDefault()));
    }

    public static String formatNow(String format) {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }

    public static String formatNow(DateTimeFormatter formatter) {
        return formatter.format(LocalDateTime.now());
    }

    public static String format(DateTimeFormatter formatter, LocalDateTime localDateTime) {
        return formatter.format(localDateTime);
    }
}