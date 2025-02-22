package org.bcdtech.skriptredis;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bcdtech.BukkitRedisMsg;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class ExprRedisMsg extends SimpleExpression<String> {

    static{
        Skript.registerExpression(ExprRedisMsg.class, String.class, ExpressionType.SIMPLE, "[the] redis (message|msg)");
    }

    @Override
    protected String @Nullable [] get(Event event) {
        BukkitRedisMsg e = (BukkitRedisMsg)event;
        return new String[]{e.getMessage()};
    }

    public Class<? extends Event>[] supportedEvents() {
        return CollectionUtils.array(BukkitRedisMsg.class);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "[the] redis (message|msg)";
    }



    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
