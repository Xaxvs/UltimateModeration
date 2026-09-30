package me.xaxis.ultimatemoderationplus.freeze;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FrozenPlayerManager {

    private final Set<UUID> frozenPlayers = new HashSet<>();

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
