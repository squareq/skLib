/*
 * Decompiled with CFR 0_122.
 * 
 * Could not load the following classes:
 *  ch.njol.skript.lang.Effect
 *  ch.njol.skript.lang.Expression
 *  ch.njol.skript.lang.SkriptParser
 *  ch.njol.skript.lang.SkriptParser$ParseResult
 *  ch.njol.util.Kleenean
 *  javax.annotation.Nullable
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package org.bcdtech.skriptredis;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.*;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;
import net.kyori.adventure.text.Component;
import org.bcdtech.Util;
import org.bukkit.event.Event;
import static org.bcdtech.Main.getRedisPublisher;
@SuppressWarnings("unused")
public class EffectSetVariable
extends AsyncEffect {
	
	static {
		Skript.registerEffect(EffectSetVariable.class, "set redis variable %string% to %string%");
	}
	
    Expression<String> key;
    Expression<String> value;


	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
        this.key = (LiteralString) arg0[0];
        this.value = (LiteralString) arg0[1];
        return true;
    }

    public String toString(@Nullable Event arg0, boolean arg1) {
        return "set redis variable";
    }

    protected void execute(final Event arg0) {
        String result = getRedisPublisher().sync().set(key.toString(), value.toString());

    }

}

