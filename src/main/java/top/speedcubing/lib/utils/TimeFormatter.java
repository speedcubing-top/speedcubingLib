package top.speedcubing.lib.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TimeFormatter {
    public long w;
    public long D;
    public long H;
    public long M;
    public long S;
    public long MS;
    String format = "";

    public TimeFormatter() {
    }

    public TimeFormatter(String format, long t) {
        this(t, TimeUnit.MILLISECONDS);
        this.format(format);
    }

    public TimeFormatter(String format, long t, TimeUnit timeUnit) {
        this(t, timeUnit);
        this.format(format);
    }

    public TimeFormatter(String format) {
        this.format(format);
    }

    public TimeFormatter(long t, TimeUnit timeUnit) {
        this.setTime(t, timeUnit);
    }

    public void setTime(long t, TimeUnit timeUnit) {
        if (Objects.requireNonNull(timeUnit) == TimeUnit.MILLISECONDS) {
            //ms
            MS = t;
            t = t / 1000;
        }

        //second
        S = t;
        t = t / 60;

        //minute
        M = t;
        t = t / 60;

        //hour
        H = t;
        t = t / 24;

        //day
        D = t;
        t = t / 7;

        //week
        w = t;
    }

    public TimeFormatter format(String format) {
        return this.format(format, false);
    }

    public TimeFormatter formatEmpty(String format) {
        return this.format(format, true);
    }

    /*
    if we say s = 3
    %s% = 03 (max=59)
    %S% = 3 (max=0x7fffffffffffffff)
    %?s% = 3 (max=59)
     */
    public TimeFormatter format(String format, boolean deleteIfEmpty) {
        if (deleteIfEmpty && this.format.isEmpty()) {
            if (w == 0 && (format.contains("%w%") || format.contains("%?w%")))
                return this;
            if (D == 0 && format.contains("%D%"))
                return this;
            if (D % 7 == 0 && format.contains("%d%") || format.contains("%?d%"))
                return this;
            if (H == 0 && format.contains("%H%"))
                return this;
            if (H % 24 == 0 && format.contains("%h%") || format.contains("%?h%"))
                return this;
            if (M == 0 && format.contains("%M%"))
                return this;
            if (M % 60 == 0 && (format.contains("%m%") || format.contains("%?m%")))
                return this;
            if (S == 0 && format.contains("%S%"))
                return this;
            if (S % 60 == 0 && (format.contains("%s%") || format.contains("%?s%")))
                return this;
            if (MS == 0 && format.contains("%MS%"))
                return this;
            if (MS % 1000 == 0 && (format.contains("%ms%") || format.contains("%?ms%")))
                return this;
        }
        format = format
                .replace("%w%", Long.toString(w))
                .replace("%d%", String.format("%03d", D % 7)).replace("%?d%", Long.toString(D % 7)).replace("%D%", Long.toString(D))
                .replace("%h%", String.format("%02d", H % 24)).replace("%?h%", Long.toString(H % 24)).replace("%H%", Long.toString(H))
                .replace("%m%", String.format("%02d", M % 60)).replace("%?m%", Long.toString(M % 60)).replace("%M%", Long.toString(M))
                .replace("%s%", String.format("%02d", S % 60)).replace("%?s%", Long.toString(S % 60)).replace("%S%", Long.toString(S))
                .replace("%ms%", String.format("%03d", MS % 1000)).replace("%?ms%", Long.toString(MS % 1000)).replace("%MS%", Long.toString(MS));
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