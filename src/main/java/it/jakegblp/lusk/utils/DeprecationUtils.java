package it.jakegblp.lusk.utils;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.util.Timespan;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;
import java.util.function.Predicate;

import static it.jakegblp.lusk.utils.Constants.*;

public class DeprecationUtils {

    static {
        Function<Timespan, Long> GET_TICKS_LOCAL;
        if (SKRIPT_HAS_TIMESPAN_TIMEPERIOD) {
            GET_TICKS_LOCAL = timespan -> timespan.getAs(Timespan.TimePeriod.TICK);
        } else {
            try {
                Method method = Timespan.class.getMethod("getTicks");
                GET_TICKS_LOCAL = timespan -> {
                    try {
                        return (Long) method.invoke(timespan);
                    } catch (IllegalAccessException | InvocationTargetException ignored) {

                    }
                    return null;
                };
            } catch (NoSuchMethodException ignored) {
                GET_TICKS_LOCAL = null;
            }
        }
        GET_TICKS = GET_TICKS_LOCAL;
    }

    public static final Function<Timespan, Long> GET_TICKS;

    /**
     * Replaces deprecated methods and avoids reflection for pre-TimePeriod implementation.
     */
    public static Timespan fromTicks(long ticks) {
        return new Timespan(ticks * 50L);
    }

    public static long getTicks(Timespan timespan) {
        return GET_TICKS.apply(timespan);
    }

    public static long getMilliseconds(Timespan timespan) {
        return getTicks(timespan) * 50;
    }

    public static <T> boolean test(Event event, Expression<T> expr, Predicate<T> predicate) {
        if (SKRIPT_2_10) {
            return expr.check(event, predicate);
        } else {
            try {
                Class<?> checkerClass = Class.forName("ch.njol.util.Checker");

                Object checkerInstance = Proxy.newProxyInstance(
                        checkerClass.getClassLoader(),
                        new Class<?>[]{checkerClass},
                        (proxy, method, args) -> {
                            if ("check".equals(method.getName()) && args.length == 1) {
                                return predicate.test((T) args[0]);
                            }
                            return null;
                        }
                );

                Method checkMethod = expr.getClass().getMethod("check", Event.class, checkerClass);
                return (boolean) checkMethod.invoke(expr, event, checkerInstance);
            } catch (InvocationTargetException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException e) {
                throw new RuntimeException("Could not create instance of Checker.", e);
            }
        }
    }

    public static <T> boolean test(Event event, Expression<T> expr, Predicate<T> predicate, @Nullable Boolean negated) {
        boolean bool = test(event, expr, predicate);
        if (negated != null) {
            return bool ^ negated;
        }
        return bool;
    }

}
