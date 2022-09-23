package top.speedcubing.lib.utils;

import java.lang.management.ManagementFactory;

public class SystemUtils {
    public static long getXmx() {
        for (String L : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            if (L.startsWith("-Xmx")) {
                long value = Long.parseLong(L.substring(4, L.length() - 1));
                switch (L.substring(L.length() - 1).toLowerCase()) {
                    case "m":
                        return value * 1024 * 1024;
                    case "g":
                        return value * 1024 * 1024 * 1024;
                }
            }
        }
        return -1;
    }

    public static long getXMS(){
        return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit();
    }
}
