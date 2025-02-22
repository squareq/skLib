/*
 * Decompiled with CFR 0_122.
 * 
 * Could not load the following classes:
 *  ch.njol.skript.lang.Expression
 *  ch.njol.skript.lang.SkriptParser
 *  ch.njol.skript.lang.SkriptParser$ParseResult
 *  ch.njol.skript.lang.util.SimpleExpression
 *  ch.njol.util.Kleenean
 *  javax.annotation.Nullable
 *  org.bukkit.event.Event
 */
package org.bcdtech.skriptredis;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;
import org.bcdtech.Util;
import org.bukkit.event.Event;

import static org.bcdtech.Main.getRedisPublisher;

@SuppressWarnings("unused")
public class ExprGetVariable
extends SimpleExpression<String> {
	
	static {
		Skript.registerExpression(ExprGetVariable.class, String.class, 
				ExpressionType.SIMPLE, "redis variable %string%");
	}
	
    private Expression<String> key;

    public Class<? extends String> getReturnType() {
        return String.class;
    }

    public boolean isSingle() {
        return false;
    }

    @SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
        this.key = (Expression<String>) arg0[0];
        return true;
    }

    public String toString(@Nullable Event arg0, boolean arg1) {
        return "get redis variable";
    }

    @Nullable
    protected String[] get(Event arg0) {
        String storedvalue = getRedisPublisher().sync().get(key.toString());
        return new String[]{Util.fixLiteralString(storedvalue)};
    }
}

