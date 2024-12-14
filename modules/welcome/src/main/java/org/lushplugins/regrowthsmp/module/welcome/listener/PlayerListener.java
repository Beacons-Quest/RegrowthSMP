package org.lushplugins.regrowthsmp.module.welcome.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.lushplugins.lushlib.libraries.chatcolor.ChatColorHandler;
import org.lushplugins.regrowthsmp.module.welcome.Welcome;

import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

public class PlayerListener implements Listener {
    private final HashSet<UUID> rewardedPlayers = new HashSet<>();
    private String playerName;
    private Long timeout;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            rewardedPlayers.clear();
            playerName = player.getName();
            timeout = Instant.now().toEpochMilli();

            if (Welcome.getInstance().getConfigManager().hasFirstJoinMessage()) {
                ChatColorHandler.broadcastMessage(Welcome.getInstance().getConfigManager().getFirstJoinMessage()
                    .replace("%player%", player.getName()));
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        if (timeout == null) {
            return;
        }

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (rewardedPlayers.contains(uuid)) {
            return;
        }

        if (timeout < Instant.now().toEpochMilli()) {
            timeout = null;
            return;
        }

        String message = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (message.contains("welcome")) {
            rewardedPlayers.add(uuid);
            player.giveExp(Welcome.getInstance().getConfigManager().getExpReward());

            if (Welcome.getInstance().getConfigManager().hasRewardMessage()) {
                ChatColorHandler.broadcastMessage(Welcome.getInstance().getConfigManager().getRewardMessage()
                    .replace("%player%", playerName));
            }
        }
    }
}
