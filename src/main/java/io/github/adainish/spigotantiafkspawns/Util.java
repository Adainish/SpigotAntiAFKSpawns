package io.github.adainish.spigotantiafkspawns;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import info.pixelmon.repack.org.spongepowered.serialize.SerializationException;
import io.leangen.geantyref.TypeToken;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.network.play.server.SCloseWindowPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;

import javax.annotation.Nonnull;
import java.util.*;

public class Util {
    public static MinecraftServer server = SpigotAntiAFKSpawns.server;

    private static final MinecraftServer SERVER = server;

    public static String msgHeader = "§7§m-----------------------------------------\n";
    public static String msgLower = "\n§7§m-----------------------------------------";

    public static boolean isPlayerOnline(ServerPlayerEntity player) {
        return isPlayerOnline(player.getUniqueID());
    }

    public static boolean isPlayerOnline(UUID uuid) {
        ServerPlayerEntity player = SERVER.getPlayerList().getPlayerByUUID(uuid);
        // IJ says it's always true ignore
        return player != null;
    }

    public static Optional <ServerPlayerEntity> getPlayerOptional(String name) {
        return Optional.ofNullable(SpigotAntiAFKSpawns.server.getPlayerList().getPlayerByUsername(name));
    }


    public static ServerPlayerEntity getPlayer(String playerName) {
        return server.getPlayerList().getPlayerByUsername(playerName);
    }

    public static ServerPlayerEntity getPlayer(UUID uuid) {
        return server.getPlayerList().getPlayerByUUID(uuid);
    }



    public static void runCommand(String cmd) {
        try {
            SERVER.getCommandManager().getDispatcher().execute(cmd, server.getCommandSource());
        } catch (CommandSyntaxException e) {
            SpigotAntiAFKSpawns.getLog().error(e);
        }
    }
    public static void playerrunCommand(ServerPlayerEntity player, String cmd) {
        try {
            SERVER.getCommandManager().getDispatcher().execute(cmd, player.getCommandSource());
        } catch (CommandSyntaxException e) {
            SpigotAntiAFKSpawns.getLog().error(e);
        }
    }


    public static MinecraftServer getInstance() {
        return SERVER;
    }

    public static void send(UUID uuid, String message) {
        getPlayer(uuid).sendMessage(new StringTextComponent(((TextUtil.getMessagePrefix()).getString() + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), uuid);
    }

    public static void send(ServerPlayerEntity player, String message) {
        if (player == null)
            return;
        player.sendMessage(new StringTextComponent(((TextUtil.getMessagePrefix()).getString() + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), player.getUniqueID());
    }

    public static void sendNoFormat(ServerPlayerEntity player, String message) {
        if (player == null)
            return;
        player.sendMessage(new StringTextComponent((message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), player.getUniqueID());
    }


    public static void sendNoFormat(CommandSource sender, String message) {
        sender.sendFeedback(new StringTextComponent((message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), false);
    }

    public static void send(CommandSource sender, String message) {
        sender.sendFeedback(new StringTextComponent(((TextUtil.getMessagePrefix()).getString() + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")), false);
    }

    public static void doBroadcast(List<String> message) {
        SERVER.getPlayerList().getPlayers().forEach(serverPlayerEntity -> {
            for (String s:message) {
                serverPlayerEntity.sendMessage(new StringTextComponent(s.replaceAll("&([0-9a-fk-or])", "\u00a7$1")), serverPlayerEntity.getUniqueID());
            }
        });
    }

    public static void doBroadcast(String message) {
        SERVER.getPlayerList().getPlayers().forEach(serverPlayerEntity -> {
            serverPlayerEntity.sendMessage(new StringTextComponent(TextUtil.getMessagePrefix().getString() + message.replaceAll("&([0-9a-fk-or])", "\u00a7$1")), serverPlayerEntity.getUniqueID());
        });
    }

    public static void doBroadcastPlain(String message) {
        SERVER.getPlayerList().getPlayers().forEach(serverPlayerEntity -> {
            serverPlayerEntity.sendMessage(new StringTextComponent(message.replaceAll("&([0-9a-fk-or])", "\u00a7$1")), serverPlayerEntity.getUniqueID());
        });
    }


    public static String formattedString(String s) {
        return s.replaceAll("&", "§");
    }

    public static List <String> formattedArrayList(List<String> list) {

        List<String> formattedList = new ArrayList <>();
        for (String s:list) {
            formattedList.add(formattedString(s));
        }

        return formattedList;
    }

    public static int getRandomNumberInRange(String moneyToGive) {
        String[] minMax = moneyToGive.split(",");
        int min = Integer.parseInt(minMax[0]);
        int max = Integer.parseInt(minMax[1]);

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return new Random().nextInt((max - min) + 1) + min;
    }


    public static int getRandomNumberInRange(int minimum, int maximum) {

        if (minimum >= maximum) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return new Random().nextInt((maximum - minimum) + 1) + minimum;
    }
}
