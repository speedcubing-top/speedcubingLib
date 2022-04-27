package speedcubing.lib.bukkit;

import net.minecraft.server.v1_8_R3.DataWatcher;

public class EntityPlayerUtils {
    public static void enableOuterLayer(DataWatcher watcher) {
        watcher.watch(10, (byte) 127);
    }
}
