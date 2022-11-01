package top.speedcubing.lib;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import top.speedcubing.lib.bukkit.CubingLibPlayer;
import top.speedcubing.lib.bukkit.inventory.Glow;
import top.speedcubing.lib.bukkit.listeners.PlayerListener;
import top.speedcubing.lib.utils.Reflections;

import java.io.File;

public class speedcubingLibBukkit extends JavaPlugin {
    public static boolean deletePlayerFile = false;
    public static final boolean is1_8_8 = Bukkit.getVersion().contains("(MC: 1.8.8)");

    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        if (is1_8_8) {
            CubingLibPlayer.init();
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
            Reflections.setClassField(Enchantment.class, "acceptingNew", true);
            try {
                Glow.glow = new Glow(100);
                Enchantment.registerEnchantment(Glow.glow);
            } catch (Exception e) {
            }
        }
    }

    public void onDisable() {
        if (deletePlayerFile) {
            File index = new File(Bukkit.getWorlds().get(0).getWorldFolder() + "/playerdata");
            File index2 = new File(Bukkit.getWorlds().get(0).getWorldFolder() + "/stats");
            if (index.list() != null)
                for (String s : index.list())
                    new File(index.getPath(), s).delete();
            if (index2.list() != null)
                for (String s : index2.list())
                    new File(index2.getPath(), s).delete();
            index.delete();
            index2.delete();
        }
    }


//    private static final Color[] colors = new Color[]{
//            new Color(0, 0, 0), new Color(0, 0, 170), new Color(0, 170, 0), new Color(0, 170, 170), new Color(170, 0, 0), new Color(170, 0, 170), new Color(255, 170, 0), new Color(170, 170, 170), new Color(85, 85, 85), new Color(85, 85, 255),
//            new Color(85, 255, 85), new Color(85, 255, 255), new Color(255, 85, 85), new Color(255, 85, 255), new Color(255, 255, 85), new Color(255, 255, 255)
//    };
//
//    public static String[] create(String url, boolean out) {
//        BufferedImage Image;
//        try {
//            Image = ImageIO.read(new URL(url));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        String[] lines = {"", "", "", "", "", "", "", ""};
//        for (int j = 0; j < 8; j++) {
//            for (int k = 0; k < 8; k++) {
//                String block = " ";
//                if (out) {
//                    ChatColor chatColor = getClosestChatColor(new Color(Image.getRGB(j + 40, k + 8), true));
//                    block = chatColor != null ? chatColor.toString() + '█' : " ";
//                }
//                if (block.equals(" ")) {
//                    ChatColor chatColor = getClosestChatColor(new Color(Image.getRGB(j + 8, k + 8), true));
//                    block = chatColor != null ? chatColor.toString() + '█' : " ";
//                }
//                lines[k] += block;
//            }
//        }
//        return lines;
//    }


//    private static ChatColor getClosestChatColor(Color color) {
//        if (color.getAlpha() < 128)
//            return null;
//        int j = 0;
//        double d1 = 1000000000;
//        for (int i = 0; i < 16; i++) {
//            double d2 = Math.abs(color.getBlue() - colors[i].getBlue()) + Math.abs(color.getRed() - colors[i].getRed()) + Math.abs(color.getGreen() - colors[i].getGreen());
//            if (d2 < d1) {
//                d1 = d2;
//                j = i;
//            }
//        }
//        return ChatColor.values()[j];
//    }
}
