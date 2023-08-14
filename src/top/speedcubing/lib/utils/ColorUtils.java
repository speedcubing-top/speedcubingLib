package top.speedcubing.lib.utils;

import top.speedcubing.lib.math.scMath;

import java.awt.*;

public class ColorUtils {

    public static Color randomColor() {
        return new Color(scMath.randomInt(0, 255), scMath.randomInt(0, 255), scMath.randomInt(0, 255));
    }

    public static String toHex(Color c) {
        return String.format("%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }
}
