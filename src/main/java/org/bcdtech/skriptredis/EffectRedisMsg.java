package org.bcdtech.skriptredis;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.LiteralString;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bcdtech.Main;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import static org.bcdtech.Util.fixLiteralString;
@SuppressWarnings("unused")
public class EffectRedisMsg extends Effect {

    Expression<String> channel;
    Expression<String> message;

    static{
        Skript.registerEffect(EffectRedisMsg.class, "send redis message %string% over channel %string%");
    }

    @Override
    protected void execute(Event event) {
        Main.sendRedisMsg(fixLiteralString(channel.toString()), fixLiteralString(message.toString()));
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "send redis message";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.message = (LiteralString) expressions[0];
        this.channel = (LiteralString) expressions[1];
        return true;
    }


}
