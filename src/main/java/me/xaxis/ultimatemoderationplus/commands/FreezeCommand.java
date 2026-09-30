package me.xaxis.ultimatemoderationplus.commands;

import me.xaxis.ultimatemoderationplus.config.ConfigSettings;
import me.xaxis.ultimatemoderationplus.lang.LangManager;
import me.xaxis.ultimatemoderationplus.player.PlayerProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

public class FreezeCommand implements CommandExecutor {

    private final LangManager langManager;
    private final ConfigSettings configSettings;
    private final PlayerProfileManager playerProfileManager;

    public FreezeCommand(LangManager langManager, ConfigSettings configSettings, PlayerProfileManager playerProfileManager) {
        this.langManager = langManager;
        this.configSettings = configSettings;
        this.playerProfileManager = playerProfileManager;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {

        //particle accelerator

        return true;
    }
}
