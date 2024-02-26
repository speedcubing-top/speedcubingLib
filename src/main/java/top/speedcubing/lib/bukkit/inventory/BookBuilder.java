package top.speedcubing.lib.bukkit.inventory;

import io.netty.buffer.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BookBuilder {
    private final Set<BaseComponent[]> pages = new HashSet<>();
    private final String title;
    private final String author;

    public BookBuilder(String title, String author) {
        this.title = title;
        this.author = author;
    }


    public BookBuilder addPage(BaseComponent... page) {
        pages.add(page);
        return this;
    }

    public ItemStack build() {
        net.minecraft.server.v1_8_R3.ItemStack nmsBook = CraftItemStack.asNMSCopy(new ItemStack(Material.WRITTEN_BOOK, 1));
        NBTTagCompound bookDescription = new NBTTagCompound();
        bookDescription.setString("title", title);
        bookDescription.setString("author", author);
        NBTTagList bookPage = new NBTTagList();
        pages.forEach(page -> bookPage.add(new NBTTagString(ComponentSerializer.toString(page))));
        bookDescription.set("pages", bookPage);
        nmsBook.setTag(bookDescription);
        return CraftItemStack.asBukkitCopy(nmsBook);
    }

    public static void openBook(ItemStack book, Player player) {
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte) 0);
        buf.writerIndex(1);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf)));
        player.getInventory().setItem(slot, old);
    }
}
