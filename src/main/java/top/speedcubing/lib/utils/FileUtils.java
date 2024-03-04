package top.speedcubing.lib.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {
    public static String getExtension(String f) {
        int index = f.lastIndexOf(46);
        index = Math.max(f.lastIndexOf(47), f.lastIndexOf(92)) > index ? -1 : index;
        return index == -1 ? "" : f.substring(index + 1);
    }

    public static void deleteDirectory(String dir) {
        deleteDirectory(new File(dir));
    }

    public static void deleteDirectory(File dir) {
        if (dir != null && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                try {
                    if (file.isDirectory())
                        deleteDirectory(file);
                    else
                        file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dir.delete();
        }
    }

    public static void copyDirectory(String fromDir, String toDir) {
        Path source = Paths.get(fromDir);
        try {
            Files.walk(source).forEach(p -> {
                try {
                    Files.copy(p, Paths.get(toDir).resolve(source.relativize(p)), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}