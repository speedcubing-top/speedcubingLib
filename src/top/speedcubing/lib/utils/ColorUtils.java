package top.speedcubing.lib.utils;

import top.speedcubing.lib.math.scMath;

import java.awt.*;

public class ColorUtils {

    public static Color toBlackAndWhite(Color c) {
        int i = (c.getBlue() + c.getGreen() + c.getRed()) / 3;
        return new Color(i, i, i);
    }

    public static Color randomColor() {
        return new Color(scMath.randomInt(0, 255), scMath.randomInt(0, 255), scMath.randomInt(0, 255));
    }
}
