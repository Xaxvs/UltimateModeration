package me.xaxis.ultimatemoderationplus.freeze;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FrozenPlayerManager {

    public FrozenPlayerManager() {
    }

    private final Set<UUID> frozenPlayers = ConcurrentHashMap.newKeySet();

    public boolean isFrozen(UUID playerId) {
        return frozenPlayers.contains(playerId);
    }

    public void addFrozenPlayer(UUID playerId) {
        frozenPlayers.add(playerId);
    }

    public List<UUID> getFrozenPlayers() {
        return frozenPlayers.stream().toList();
    }

    public void removeFrozenPlayer(UUID playerId) {
        frozenPlayers.remove(playerId);
    }
}
