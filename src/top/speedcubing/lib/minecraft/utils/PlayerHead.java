package top.speedcubing.lib.minecraft.utils;


import org.bukkit.ChatColor;
import top.speedcubing.lib.api.MojangAPI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class PlayerHead {


    private static final Color[] colors = new Color[]{
            new Color(0, 0, 0), new Color(0, 0, 170), new Color(0, 170, 0), new Color(0, 170, 170), new Color(170, 0, 0), new Color(170, 0, 170), new Color(255, 170, 0), new Color(170, 170, 170), new Color(85, 85, 85), new Color(85, 85, 255),
            new Color(85, 255, 85), new Color(85, 255, 255), new Color(255, 85, 85), new Color(255, 85, 255), new Color(255, 255, 85), new Color(255, 255, 255)
    };

    public static String[] create(String uuid, boolean outLayer) {
        BufferedImage image;
        try {
            image = ImageIO.read(new URL(MojangAPI.getTextureByUUID(uuid).getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String[] lines = {"", "", "", "", "", "", "", ""};
        ChatColor chatColor;
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                chatColor = getClosestChatColor(new Color(image.getRGB(j + 40, k + 8), true));
                lines[k] += chatColor != null ? chatColor : getClosestChatColor(new Color(image.getRGB(j + 8, k + 8), true));
                lines[k] += '█';
            }
        }
        return lines;
    }


    private static ChatColor getClosestChatColor(Color color) {
        if (color.getAlpha() < 128)
            return null;
        int j = 0;
        double d1 = 1000000000;
        for (int i = 0; i < 16; i++) {
            double d2 = Math.abs(color.getBlue() - colors[i].getBlue()) + Math.abs(color.getRed() - colors[i].getRed()) + Math.abs(color.getGreen() - colors[i].getGreen());
            if (d2 < d1) {
                d1 = d2;
                j = i;
            }
        }
        return ChatColor.values()[j];
    }
}
