package me.xaxis.ultimatemoderationplus.listener;

import me.xaxis.ultimatemoderationplus.freeze.FrozenPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class OnPlayerMove implements Listener {

    private final FrozenPlayerManager frozenPlayerManager;

    public OnPlayerMove(FrozenPlayerManager frozenPlayerManager) {
        this.frozenPlayerManager = frozenPlayerManager;
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        if(frozenPlayerManager.isFrozen(playerId)) {
            event.setCancelled(true);
        }
    }
}
