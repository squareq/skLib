package org.bcdtech.skriptredis;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.bcdtech.Main.getPluginInstance;

public class ExprChannelList extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprChannelList.class, String.class, ExpressionType.SIMPLE, "[the] redis channels");
    }
    @Override
    protected String @Nullable [] get(Event event) {
        @NotNull List<String> channels = getPluginInstance().getConfig().getStringList("redischannels");
        return channels.toArray(new String[channels.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "list of channels";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
