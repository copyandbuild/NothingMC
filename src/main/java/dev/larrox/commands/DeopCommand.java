package dev.larrox.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

public class DeopCommand extends Command {

    public DeopCommand() {
        super("deop");

        setDefaultExecutor((sender, context) -> sender.sendMessage("§8[§fNothingMC§8] §7Use §e/deop §6<player>"));

        var targetArgument = ArgumentType.String("Player");

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                String targetName = context.get("Player");

                Player targetPlayer = MinecraftServer.getConnectionManager().getOnlinePlayers().stream()
                        .filter(p -> p.getUsername().equals(targetName))
                        .findFirst()
                        .orElse(null);

                if (targetPlayer != null) {
                    targetPlayer.setPermissionLevel(1);
                    player.sendMessage("§8[§fNothingMC§8] §7You have removed OP from §e" + targetPlayer.getUsername());
                } else {
                    player.sendMessage("§8[§fNothingMC§8] §7Player §e" + targetName + " §7is not online.");
                }
            } else {
                sender.sendMessage("§8[§fNothingMC§8] §7Only players can execute this command.");
            }
        }, targetArgument);
    }
}