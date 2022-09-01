package top.speedcubing.lib.bukkit;

import top.speedcubing.lib.utils.Reflections;
import net.minecraft.server.v1_8_R3.GameRules;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class WorldUtils {
    public static void clearGameRules(World world) {
        GameRules m = new GameRules();
        TreeMap<String, ?> map = (TreeMap<String, ?>) Reflections.getField(((CraftWorld) world).getHandle().getGameRules(), "a");
        Set<String> remove = new HashSet<>();
        map.keySet().forEach(a -> {
            if (!m.contains(a)) remove.add(a);
        });
        remove.forEach(map::remove);
    }

    public static void resetGameRules(World world) {
        Reflections.setField(((CraftWorld) world).getHandle().worldData, "K", new GameRules());
    }

    public static void fillAir(int x1, int y1, int z1, int x2, int y2, int z2, World world) {
        int a;
        if(x1 > x2){
            a = x2;
            x2 = x1;
            x1 = a;
        }
        if(y1 > y2){
            a = y2;
            y2 = y1;
            y1 = a;
        }
        if(z1 > z2){
            a = z2;
            z2 = z1;
            z1 = a;
        }
        for (int X = x1; X <= x2; X++) {
            for (int Y = y1; Y <= y2; Y++) {
                for (int Z = z1; Z <= z2; Z++) {
                    world.getBlockAt(X, Y, Z).setType(Material.AIR);
                }
            }
        }
    }

    public static void cloneArea(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, World world) {
        int a;
        if (x1 > x2) {
            a = x2;
            x2 = x1;
            x1 = a;
            x3 = x3 - (x2 - x1);
        }
        if (y1 > y2) {
            a = y2;
            y2 = y1;
            y1 = a;
            y3 = y3 - (y2 - y1);
        }
        if (z1 > z2) {
            a = z2;
            z2 = z1;
            z1 = a;
            z3 = z3 - (z2 - z1);
        }
        for (int X = x1; X <= x2; X++) {
            for (int Y = y1; Y <= y2; Y++) {
                for (int Z = z1; Z <= z2; Z++) {
                    Block b1 = world.getBlockAt(X, Y, Z);
                    Block b2 = world.getBlockAt(x3 + X - x1, y3 + Y - y1, z3 + Z - z1);
                    b2.setType(b1.getType());
                    b2.setData(b1.getData());
                }
            }
        }
    }
}
