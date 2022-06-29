package speedcubing.lib.bukkit.packetwrapper;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import speedcubing.lib.utils.Reflections;

public class OutPlayerListHeaderFooter {
    public final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

    public OutPlayerListHeaderFooter() {
    }

    public OutPlayerListHeaderFooter a(IChatBaseComponent header) {
        Reflections.setField(packet, "a", header);
        return this;
    }

    public OutPlayerListHeaderFooter b(IChatBaseComponent footer) {
        Reflections.setField(packet, "b", footer);
        return this;
    }

    public OutPlayerListHeaderFooter a(String header) {
        return a(new ChatComponentText(header));
    }

    public OutPlayerListHeaderFooter b(String footer) {
        return b(new ChatComponentText(footer));
    }
}
