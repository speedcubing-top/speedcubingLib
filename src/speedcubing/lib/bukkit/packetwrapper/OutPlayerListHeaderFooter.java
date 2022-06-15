package speedcubing.lib.bukkit.packetwrapper;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import speedcubing.lib.utils.Reflections;

public class OutPlayerListHeaderFooter {
    public static PacketPlayOutPlayerListHeaderFooter a(String header, String footer) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        Reflections.setField(packet, "a", new ChatComponentText(header));
        Reflections.setField(packet, "b", new ChatComponentText(footer));
        return packet;
    }
}
