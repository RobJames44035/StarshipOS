/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package test.java.time.zone;

import java.time.*;
import java.time.zone.*;
import java.util.*;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

/**
 * @summary ZoneRules invariants can be broken.
 *
 * @bug 8246788
 */
@Test
public class TestMutableZoneRules {
    static final ZoneOffset offset = ZoneOffset.ofHoursMinutes(1, 30);

    static final ZoneOffsetTransitionRule rule1 =
        ZoneOffsetTransitionRule.of(Month.APRIL, 2, DayOfWeek.TUESDAY, LocalTime.MIN, true,
                ZoneOffsetTransitionRule.TimeDefinition.UTC, offset, offset, offset);

    static final ZoneOffsetTransitionRule rule2 =
        ZoneOffsetTransitionRule.of(Month.MARCH, 2, DayOfWeek.MONDAY, LocalTime.MIN, true,
                ZoneOffsetTransitionRule.TimeDefinition.UTC, offset, offset, offset);

    public void testMutation() {
        ZoneOffsetTransitionRule[] array = { rule1 };
        ZoneRules zr1 = ZoneRules.of(offset, offset, List.of(), List.of(), List.of(rule1));
        ZoneRules zr2 = ZoneRules.of(offset, offset, List.of(), List.of(), new TestList(array, array.length));

        assertEquals(zr2, zr1);
        array[0] = rule2;
        assertEquals(zr2, zr1);
    }

    public void testLength() {
        ZoneOffsetTransitionRule[] array = new ZoneOffsetTransitionRule[17];
        Arrays.setAll(array, i -> rule1);

        assertThrows(IllegalArgumentException.class,
            () -> ZoneRules.of(offset, offset, List.of(), List.of(), new TestList(array, 1)));
    }

    static class TestList extends AbstractList<ZoneOffsetTransitionRule> {
        final ZoneOffsetTransitionRule[] array;
        final int size;

        TestList(ZoneOffsetTransitionRule[] array, int size) {
            this.array = array;
            this.size = size;
        }

        public int size()                           { return size; }
        public ZoneOffsetTransitionRule get(int i)  { return array[i]; }
        public Object[] toArray()                   { return array; }

        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a)               { return (T[]) array; }
    }
}
