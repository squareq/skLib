package org.bcdtech.skriptredis;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.LiteralString;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import static org.bcdtech.Main.getRedisPublisher;
@SuppressWarnings("unused")
public class EffectDeleteVariable extends Effect {

    Expression<String> key;

    static {
        Skript.registerEffect(EffectDeleteVariable.class, "delete redis variable %string%");
    }
    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "delete redis variable";
    }

    @Override
    protected void execute(Event event) {
        getRedisPublisher().async().del(key.toString());
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.key = (LiteralString) expressions[0];
        return true;
    }
}
