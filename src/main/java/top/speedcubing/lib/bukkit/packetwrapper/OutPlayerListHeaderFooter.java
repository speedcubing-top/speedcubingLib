package top.speedcubing.lib.bukkit.packetwrapper;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import top.speedcubing.lib.utils.ReflectionUtils;

public class OutPlayerListHeaderFooter {
    public final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

    public OutPlayerListHeaderFooter() {
    }

    public OutPlayerListHeaderFooter a(IChatBaseComponent header) {
        ReflectionUtils.setField(packet, "a", header);
        return this;
    }

    public OutPlayerListHeaderFooter b(IChatBaseComponent footer) {
        ReflectionUtils.setField(packet, "b", footer);
        return this;
    }

    public OutPlayerListHeaderFooter a(String header) {
        return a(new ChatComponentText(header));
    }

    public OutPlayerListHeaderFooter b(String footer) {
        return b(new ChatComponentText(footer));
    }
}
