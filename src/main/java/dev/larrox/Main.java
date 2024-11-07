package dev.larrox;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.ping.ResponseData;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {

        MinecraftServer server = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer(new AnvilLoader("worlds/world"));

        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 41, Block.GRASS_BLOCK));

        instanceContainer.setChunkSupplier(LightingChunk::new);

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 41, 0));
        });

        globalEventHandler.addListener(PlayerBlockBreakEvent.class, event -> {

            var material = event.getBlock().registry().material();

            if (material != null) {
                var itemstack = ItemStack.of(material);
                ItemEntity itementity = new ItemEntity(itemstack);
                itementity.setInstance(event.getInstance(), event.getBlockPosition().add(0.5, 0.6, 0.5));
                itementity.setPickupDelay(Duration.ofMillis(500));
            }
        });

        globalEventHandler.addListener(ItemDropEvent.class, event -> {
            ItemEntity itemEntity = new ItemEntity(event.getItemStack());
            itemEntity.setInstance(event.getPlayer().getInstance(), event.getPlayer().getPosition().add(0, 1.2, 0.5));
            itemEntity.setVelocity(event.getPlayer().getPosition().add(0, 2, 0).direction().mul(5));
            itemEntity.setPickupDelay(Duration.ofMillis(500));
        });

        globalEventHandler.addListener(PickupItemEvent.class, event -> {
            ItemEntity itemEntity = new ItemEntity(event.getItemStack());
            Player player = (Player) event.getLivingEntity();
            player.getInventory().addItemStack(itemEntity.getItemStack());
        });

        globalEventHandler.addListener(ServerListPingEvent.class, event -> {
            event.setResponseData(new ResponseData());
            event.getResponseData().addEntries(MinecraftServer.getConnectionManager().getOnlinePlayers());
            event.getResponseData().setDescription("a §f§nNothingMC§7 Server.");
        });

        MojangAuth.init();
        server.start("0.0.0.0", 25565);

    }
}