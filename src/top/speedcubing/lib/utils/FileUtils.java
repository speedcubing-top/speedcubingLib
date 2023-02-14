package top.speedcubing.lib.utils;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldServer;

import java.io.File;
import java.util.UUID;

public class FileUtils {
    public static String getExtension(String s) {
        int index = s.lastIndexOf(46);
        index = Math.max(s.lastIndexOf(47), s.lastIndexOf(92)) > index ? -1 : index;
        return index == -1 ? "" : s.substring(index + 1);
    }

    public static void deleteDirectory(File directory) {
        for (File file : directory.listFiles()) {
            try {
                if (file.isDirectory())
                    deleteDirectory(file);
                else
                    file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        directory.delete();
    }
}
