package dev.larrox.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

public class GamemodeCommand extends Command {
    public GamemodeCommand() {
        super("gamemode", "gmode", "gm");

        setDefaultExecutor((sender, context) -> sender.sendMessage("§8[§fNothingMC§8] §7Use §e/gamemode §6<0/1/2/3>"));

        var gamemode = ArgumentType.Integer("0/1/2/3");

        addSyntax(((sender, context) -> {
            final int number = context.get(gamemode);
            if (sender instanceof Player player) {
                GameMode gameMode;
                String gamemodename;

                switch (number) {
                    case 0:
                        gameMode = GameMode.SURVIVAL;
                        gamemodename = "Survival";
                        break;
                    case 1:
                        gameMode = GameMode.CREATIVE;
                        gamemodename = "Creative";
                        break;
                    case 2:
                        gameMode = GameMode.ADVENTURE;
                        gamemodename = "Adventure";
                        break;
                    case 3:
                        gameMode = GameMode.SPECTATOR;
                        gamemodename = "Spectator";
                        break;
                    default:
                        return;
                }

                if (player.getGameMode() == gameMode) {
                    return;
                }

                player.setGameMode(gameMode);
                player.sendMessage(Component.text("§8[§fNothingMC§8] §7Your Gamemode has been updated to §f§o" + gamemodename));
            }
        }), gamemode);
    }
}
