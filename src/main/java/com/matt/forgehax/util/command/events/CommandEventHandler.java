package com.matt.forgehax.util.command.events;

import com.google.common.collect.Sets;
import com.matt.forgehax.asm.events.PacketEvent;
import com.matt.forgehax.util.command.CommandExecutor;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;

/**
 * Created on 5/15/2017 by fr1kin
 */
public class CommandEventHandler {
    private static final Character ACTIVATION_CHARACTER = '.';

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {

    }

    @SubscribeEvent
    public void onSendPacket(PacketEvent.Outgoing.Pre event) {
        if(event.getPacket() instanceof CPacketChatMessage) {
            String message = ((CPacketChatMessage) event.getPacket()).getMessage();
            if(message.startsWith(ACTIVATION_CHARACTER.toString()) && message.length() > 1) {
                // cut out the . from the message
                String line = message.substring(1);
                CommandExecutor.run(line);
                event.setCanceled(true);
            }
        }
    }

    //
    // ignore this stuff
    //

    private static CommandEventHandler instance = null;

    public static void register() {
        if(instance == null) MinecraftForge.EVENT_BUS.register(instance = new CommandEventHandler());
    }

    public static void unregister() {
        if(instance != null) {
            MinecraftForge.EVENT_BUS.register(instance);
            instance = null;
        }
    }

    public static boolean isRegistered() {
        return instance != null;
    }
}