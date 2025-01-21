/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6276988
 * @summary All enum constants in a class should share a single "universe".
 * @modules java.base/java.util:open
 */

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.util.EnumSet;

public class OneUniverse {

    private static final Field universeField;

    static {
        try {
            universeField = EnumSet.class.getDeclaredField("universe");
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
        universeField.setAccessible(true);
    }

    public static void main(String... args) {

        EnumSet<RoundingMode> noneSet = EnumSet.noneOf(RoundingMode.class);
        EnumSet<RoundingMode> allSet  = EnumSet.allOf(RoundingMode.class);

        if (getUniverse(noneSet) != getUniverse(allSet)) {
            throw new AssertionError(
                    "Multiple universes detected.  Inform the bridge!");
        }
    }

    private static <E extends Enum<E>> Enum<E>[] getUniverse(EnumSet<E> set) {
        try {
            return (Enum<E>[]) universeField.get(set);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }
}
