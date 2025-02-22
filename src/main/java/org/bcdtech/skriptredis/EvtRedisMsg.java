package org.bcdtech.skriptredis;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import org.bcdtech.BukkitRedisMsg;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import org.jetbrains.annotations.Nullable;
@SuppressWarnings("unused")
public class EvtRedisMsg extends SkriptEvent {

    static {
        Skript.registerEvent("Redis Receive Event", EvtRedisMsg.class, BukkitRedisMsg.class, "redis (message|msg)");
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";
    }
}
