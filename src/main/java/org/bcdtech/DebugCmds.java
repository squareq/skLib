package org.bcdtech;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.lettuce.core.RedisFuture;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.command.CommandSender;

import static org.bcdtech.Main.*;

public class DebugCmds {


    public static LiteralCommandNode<CommandSourceStack> createRedisDebugCmd() {
        return Commands.literal("cheesedebugredis")
                .requires(source -> source.getSender().hasPermission("Admin"))
                .executes(ctx -> {
                    final CommandSender sender = ctx.getSource().getSender();
                    Main.sendRedisMsg("channel", "Hello, I love cats.");
                    //sender.sendPlainMessage("Channels: " + getRedisCommandExecutor().pubsubChannels());
                    //sender.sendPlainMessage("Connected to redis: " + connectedToRedis());

                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }



    public static void registerDebugCmd(){
        getPluginInstance().getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(DebugCmds.createRedisDebugCmd(), "Debug for skript addon Redis");
        });
    }
}
