package io.github.adainish.spigotantiafkspawns;

import com.pixelmonmod.pixelmon.Pixelmon;
import de.kinglol12345.AntiAFKPlus.api.AntiAFKPlusAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("spigotantiafkspawns")
public class SpigotAntiAFKSpawns {

    public static final String MOD_NAME = "AntiAFKSpawns";
    public static final String VERSION = "1.0";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2022";
    private static final Logger log = LogManager.getLogger(MOD_NAME);
    public static AntiAFKPlusAPI api;
    public static MinecraftServer server;
    public SpigotAntiAFKSpawns() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static Logger getLog() {
        return log;
    }

    private void setup(final FMLCommonSetupEvent event) {
        getLog().info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        Pixelmon.EVENT_BUS.register(new SpawnListener());
    }


    @SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent event) {
        api = AntiAFKPlusAPI.getAPI();
        server = ServerLifecycleHooks.getCurrentServer();
    }

}
