/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package tck.java.time.zone.serial;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;

import org.testng.annotations.Test;
import tck.java.time.AbstractTCKTest;

/**
 * Test ZoneOffsetTransitionRule serialization.
 */
@Test
public class TCKZoneOffsetTransitionRuleSerialization extends AbstractTCKTest {

    private static final LocalTime TIME_0100 = LocalTime.of(1, 0);
    private static final ZoneOffset OFFSET_0200 = ZoneOffset.ofHours(2);
    private static final ZoneOffset OFFSET_0300 = ZoneOffset.ofHours(3);


    //-----------------------------------------------------------------------
    // Test serialization
    //-----------------------------------------------------------------------
    @Test
    public void test_serialization_unusualOffsets() throws Exception {
        ZoneOffsetTransitionRule test = ZoneOffsetTransitionRule.of(
                Month.MARCH, 20, null, TIME_0100, false, TimeDefinition.STANDARD,
                ZoneOffset.ofHoursMinutesSeconds(-12, -20, -50),
                ZoneOffset.ofHoursMinutesSeconds(-4, -10, -34),
                ZoneOffset.ofHours(-18));
        assertSerializable(test);
    }

    @Test
    public void test_serialization_endOfDay() throws Exception {
        ZoneOffsetTransitionRule test = ZoneOffsetTransitionRule.of(
                Month.MARCH, 20, DayOfWeek.FRIDAY, LocalTime.MIDNIGHT, true, TimeDefinition.UTC,
                OFFSET_0200, OFFSET_0200, OFFSET_0300);
        assertSerializable(test);
    }

    @Test
    public void test_serialization_unusualTime() throws Exception {
        ZoneOffsetTransitionRule test = ZoneOffsetTransitionRule.of(
                Month.MARCH, 20, DayOfWeek.WEDNESDAY, LocalTime.of(13, 34, 56), false, TimeDefinition.STANDARD,
                OFFSET_0200, OFFSET_0200, OFFSET_0300);
        assertSerializable(test);
    }

    @Test
    public void test_serialization_floatingWeek() throws Exception {
        ZoneOffsetTransitionRule test = ZoneOffsetTransitionRule.of(
                Month.MARCH, 20, DayOfWeek.SUNDAY, TIME_0100, false, TimeDefinition.WALL,
                OFFSET_0200, OFFSET_0200, OFFSET_0300);
        assertSerializable(test);
    }

    @Test
    public void test_serialization_floatingWeekBackwards() throws Exception {
        ZoneOffsetTransitionRule test = ZoneOffsetTransitionRule.of(
                Month.MARCH, -1, DayOfWeek.SUNDAY, TIME_0100, false, TimeDefinition.WALL,
                OFFSET_0200, OFFSET_0200, OFFSET_0300);
        assertSerializable(test);
    }

    @Test
    public void test_serialization_fixedDate() throws Exception {
        ZoneOffsetTransitionRule test = ZoneOffsetTransitionRule.of(
                Month.MARCH, 20, null, TIME_0100, false, TimeDefinition.WALL,
                OFFSET_0200, OFFSET_0200, OFFSET_0300);
        assertSerializable(test);
    }

    @Test
    public void test_invalid_serialform() throws Exception {
        assertNotSerializable(ZoneOffsetTransitionRule.class);
    }

}
