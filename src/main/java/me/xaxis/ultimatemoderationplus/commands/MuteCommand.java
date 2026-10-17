package me.xaxis.ultimatemoderationplus.commands;

import me.xaxis.ultimatemoderationplus.config.ConfigSettings;
import me.xaxis.ultimatemoderationplus.constants.ConfigConstants;
import me.xaxis.ultimatemoderationplus.constants.ModerationConstants;
import me.xaxis.ultimatemoderationplus.infractions.Mute;
import me.xaxis.ultimatemoderationplus.lang.Lang;
import me.xaxis.ultimatemoderationplus.lang.LangManager;
import me.xaxis.ultimatemoderationplus.lang.Placeholders;
import me.xaxis.ultimatemoderationplus.permissions.Permissions;
import me.xaxis.ultimatemoderationplus.player.PlayerProfile;
import me.xaxis.ultimatemoderationplus.player.PlayerProfileManager;
import me.xaxis.ultimatemoderationplus.utils.Tuple;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class MuteCommand implements CommandExecutor {

    private final LangManager langManager;
    private final PlayerProfileManager playerProfileManager;
    private final ConfigSettings configSettings;

    public MuteCommand(LangManager langManager, PlayerProfileManager playerProfileManager, ConfigSettings configSettings) {
        this.langManager = langManager;
        this.playerProfileManager = playerProfileManager;
        this.configSettings = configSettings;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {

        Tuple<UUID, String> identity = validateSender(sender);

        if (identity == null) {
            sender.sendMessage(langManager.getMessage(Lang.SENDER_NOT_VALID));
            return true;
        }

        if (!sender.hasPermission(Permissions.MUTE_COMMAND.getPermission())) {
            sender.sendMessage(langManager.getMessage(Lang.NO_PERMISSION));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(langManager.getMessage(Lang.MUTE_USAGE));
            return true;
        }

        String targetName = args[0];
        PlayerProfile playerProfile = playerProfileManager.getPlayerProfile(targetName);
        if (playerProfile == null) {
            sender.sendMessage(langManager.replacePlaceholders(
                    langManager.getMessage(Lang.PLAYER_NOT_FOUND),
                    Map.of(
                            Placeholders.PLAYER, targetName
                    )
            ));
            return true;
        }

        String reason = String.join(
                "",
                Arrays.copyOfRange(args, 1, args.length)
        );

        if (reason.isBlank()) {
            sender.sendMessage(langManager.getMessage(Lang.MUTE_MUST_HAVE_REASON));
            return true;
        }

        if (reason.length() > configSettings.maxContentLength()) {
            sender.sendMessage(langManager.replacePlaceholders(
                    langManager.getMessage(Lang.CONTENT_TOO_LONG),
                    Map.of(
                            Placeholders.CONTENT_MAX_LENGTH, String.valueOf(configSettings.maxContentLength())
                    ))
            );
            return true;
        }

        Mute mute = playerProfileManager.getMute(playerProfile);

        if (mute != null) {
            sender.sendMessage(langManager.getMessage(Lang.PLAYER_ALREADY_MUTED));
            return true;
        }

        playerProfileManager.muteProfile(playerProfile, new Mute(
                identity.first(),
                identity.second(),
                reason,
                System.currentTimeMillis(),
                playerProfile.playerId(),
                ConfigConstants.PERMANENT_DURATION
        ));

        sender.sendMessage(langManager.replacePlaceholders(
                langManager.getMessage(Lang.MUTED_PLAYER),
                Map.of(
                        Placeholders.PLAYER, targetName,
                        Placeholders.REASON, reason
                )
        ));

        return true;
    }

    private Tuple<UUID, String> validateSender(CommandSender sender) {
        if (sender instanceof Player player) {
            return new Tuple<>(player.getUniqueId(), player.getName());
        } else if (sender instanceof ConsoleCommandSender) {
            return new Tuple<>(ModerationConstants.CONSOLE_UUID, ModerationConstants.CONSOLE_NAME);
        } else return null;
    }
}
