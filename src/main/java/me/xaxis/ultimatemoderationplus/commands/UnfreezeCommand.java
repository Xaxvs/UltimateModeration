package me.xaxis.ultimatemoderationplus.commands;

import me.xaxis.ultimatemoderationplus.freeze.FrozenPlayerManager;
import me.xaxis.ultimatemoderationplus.lang.Lang;
import me.xaxis.ultimatemoderationplus.lang.LangManager;
import me.xaxis.ultimatemoderationplus.lang.Placeholders;
import me.xaxis.ultimatemoderationplus.permissions.Permissions;
import me.xaxis.ultimatemoderationplus.player.PlayerProfile;
import me.xaxis.ultimatemoderationplus.player.PlayerProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.UUID;

public class UnfreezeCommand implements CommandExecutor {

    private final LangManager langManager;
    private final PlayerProfileManager playerProfileManager;
    private final FrozenPlayerManager frozenPlayerManager;

    public UnfreezeCommand(LangManager langManager, PlayerProfileManager playerProfileManager, FrozenPlayerManager frozenPlayerManager) {
        this.langManager = langManager;
        this.playerProfileManager = playerProfileManager;
        this.frozenPlayerManager = frozenPlayerManager;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String @NonNull [] args) {

        String senderName;

        if (sender instanceof Player player) {
            senderName = player.getName();
        } else if (sender instanceof ConsoleCommandSender) {
            senderName = "Console";
        } else {
            sender.sendMessage(langManager.getMessage(Lang.SENDER_NOT_VALID));
            return true;
        }

        if (!sender.hasPermission(Permissions.UNFREEZE_COMMAND.getPermission())) {
            sender.sendMessage(langManager.getMessage(Lang.NO_PERMISSION));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(langManager.getMessage(Lang.UNFREEZE_USAGE));
            return true;
        }

        String playerName = args[0];

        PlayerProfile playerProfile = playerProfileManager.getPlayerProfile(playerName);
        if (playerProfile == null) {
            sender.sendMessage(langManager.replacePlaceholders(
                    langManager.getMessage(Lang.PLAYER_NOT_FOUND),
                    Map.of(Placeholders.PLAYER, playerName)
            ));
            return true;
        }

        Player targetPlayer = Bukkit.getServer().getPlayer(playerProfile.playerId());
        UUID playerId = playerProfile.playerId();
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            sender.sendMessage(langManager.getMessage(Lang.PLAYER_NOT_ONLINE));
            return true;
        }

        if (!frozenPlayerManager.isFrozen(playerId)) {
            sender.sendMessage(langManager.getMessage(Lang.PLAYER_NOT_FROZEN));
            return true;
        }

        frozenPlayerManager.removeFrozenPlayer(playerId);
        sender.sendMessage(langManager.replacePlaceholders(
                langManager.getMessage(Lang.UNFROZE_PLAYER),
                Map.of(
                        Placeholders.PLAYER, playerName
                )
        ));
        targetPlayer.sendMessage(
                langManager.replacePlaceholders(
                        langManager.getMessage(Lang.YOU_ARE_UNFROZEN),
                        Map.of(
                                Placeholders.STAFF, senderName
                        )
                )
        );
        targetPlayer.resetTitle();

        return true;
    }
}
