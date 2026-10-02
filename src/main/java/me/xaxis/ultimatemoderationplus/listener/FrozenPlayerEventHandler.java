package me.xaxis.ultimatemoderationplus.listener;

import me.xaxis.ultimatemoderationplus.config.ConfigSettings;
import me.xaxis.ultimatemoderationplus.freeze.FrozenPlayerManager;
import me.xaxis.ultimatemoderationplus.lang.Lang;
import me.xaxis.ultimatemoderationplus.lang.LangManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class FrozenPlayerEventHandler implements Listener {

    private final FrozenPlayerManager frozenPlayerManager;
    private final ConfigSettings configSettings;
    private final LangManager langManager;

    public FrozenPlayerEventHandler(FrozenPlayerManager frozenPlayerManager, ConfigSettings configSettings, LangManager langManager) {
        this.frozenPlayerManager = frozenPlayerManager;
        this.configSettings = configSettings;
        this.langManager = langManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        if (!frozenPlayerManager.isFrozen(uuid)) return;
        if (configSettings.allowChatWhileFrozen()) return;
        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerInventoryInteract(InventoryInteractEvent event) {
        UUID uuid = event.getWhoClicked().getUniqueId();

        if (frozenPlayerManager.isFrozen(uuid)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        if (frozenPlayerManager.isFrozen(playerId)) {
            event.getPlayer().sendTitle(
                    langManager.getMessage(Lang.FREEZE_TITLE),
                    langManager.getMessage(Lang.FREEZE_SUBTITLE),
                    1, 20 * 60 * 60 * 24, 1
            );
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        if (frozenPlayerManager.isFrozen(playerId)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        if (!frozenPlayerManager.isFrozen(playerId)) return;
        event.setCancelled(true);

    }
}
