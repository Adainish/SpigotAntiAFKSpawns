package io.github.adainish.spigotantiafkspawns;

import com.pixelmonmod.pixelmon.api.events.spawning.LegendarySpawnEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpawnListener {

    @SubscribeEvent
    public void onLegendarySpawn(LegendarySpawnEvent.ChoosePlayer event) {
        Player player = getPlayer(event.player.getUniqueID());
        if (player == null) {
            return;
        }
        if (isAFK(player.getUniqueId())) {
            ServerPlayerEntity newPlayer = getRandomNonAFKPlayer();
            ServerPlayerEntity afkPlayer = event.player;
            event.player = getRandomNonAFKPlayer();
            doAnnouncement(afkPlayer, newPlayer);
        }
    }


    public void doAnnouncement(ServerPlayerEntity afkPlayer, ServerPlayerEntity newPlayer) {
        List<String> strings = new ArrayList <>();
        strings.add("&2&l&m------------------ &e&lANTI AFK &2&l&m------------------");
        strings.add("   &7A Legendary &7attempted to spawn");
        strings.add("   &7on &2%afkplayer% &7but they are currently &c&lAFK!".replace("%afkplayer%", afkPlayer.getName().getUnformattedComponentText()));
        strings.add(" The Legendary will instead spawn on &2%newplayer%".replace("%newplayer%", newPlayer.getName().getUnformattedComponentText()));
        strings.add("&2&l&m--------------------------------------------------------");
        Util.doBroadcast(strings);
    }


    public ServerPlayerEntity getRandomNonAFKPlayer() {
        List <ServerPlayerEntity> playerEntityList = new ArrayList <>();
        for (ServerPlayerEntity pl:SpigotAntiAFKSpawns.server.getPlayerList().getPlayers()) {
            if (isAFK(pl.getUniqueID()))
                continue;
            playerEntityList.add(pl);
        }
        return RandomHelper.getRandomElementFromCollection(playerEntityList);
    }

    public boolean isAFK(UUID uuid) {
        return SpigotAntiAFKSpawns.api.isAFK(getPlayer(uuid));
    }

    public Player getPlayer(UUID uuid) {
        for (Player p : SpigotAntiAFKSpawns.api.getAFKPlayers()) {
            if (p.getUniqueId().equals(uuid))
                return p;
        }
        return null;
    }
}
