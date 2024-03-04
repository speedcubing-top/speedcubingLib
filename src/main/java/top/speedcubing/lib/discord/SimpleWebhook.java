package top.speedcubing.lib.discord;

import java.awt.*;
import java.io.IOException;

public class SimpleWebhook {
    public static void sendWebhook(String webhookUrl, String title, String description, String imageUrl, Color color) {
        DiscordWebhook discordWebhook = new DiscordWebhook(webhookUrl);
        discordWebhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(title)
                .setDescription(description)
                .setImage(imageUrl)
                .setColor(color));
        try {
            discordWebhook.execute();
        } catch (IOException en) {
            throw new RuntimeException(en);
        }
    }
}