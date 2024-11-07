package dev.larrox.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Weather;

public class WeatherCommand extends Command {
    public WeatherCommand() {
        super("weather");

        setDefaultExecutor((sender, context) -> sender.sendMessage("§8[§fNothingMC§8] §7Use §e/weather §6<clear/rain/thunder>"));

        var weathertype = ArgumentType.String("<clear/rain/thunder>");

        addSyntax(((sender, context) -> {
            final String weatherYEA = context.get(weathertype);
            if (sender instanceof Player player) {
                Weather Weather1;
                String weathername;

                switch (weatherYEA) {
                    case "clear":
                        Weather1 = Weather.CLEAR;
                        weathername = "Sunny";
                        break;
                    case "rain":
                        Weather1 = Weather.RAIN;
                        weathername = "Rain";
                        break;
                    case "thunder":
                        Weather1 = Weather.THUNDER;
                        weathername = "Thunder";
                        break;
                    default:
                        return;
                }

                if (player.getInstance().getWeather() == Weather1) {
                    return;
                }

                player.getInstance().setWeather(Weather1);
                player.sendMessage(Component.text("§8[§fNothingMC§8] §7The Weather has been updated to §f§o" + weathername));
            }
        }), weathertype);
    }
}
