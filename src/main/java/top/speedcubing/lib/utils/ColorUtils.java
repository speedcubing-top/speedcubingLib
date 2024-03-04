package top.speedcubing.lib.utils;

import java.awt.Color;
import top.speedcubing.lib.math.scMath;

public class ColorUtils {

    public static Color randomColor() {
        return new Color(scMath.randomInt(0, 255), scMath.randomInt(0, 255), scMath.randomInt(0, 255));
    }

    public static String toHex(Color c) {
        return String.format("%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }

    public static int[] rgbToColor(int rgb) {
        return new int[]{rgb >> 16 & 255, rgb >> 8 & 255, rgb & 255, rgb >> 24 & 255};
    }

    public static int colorToRGB(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
    }
}
