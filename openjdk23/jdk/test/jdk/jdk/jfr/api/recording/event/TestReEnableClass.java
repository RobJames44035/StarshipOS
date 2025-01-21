/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.event;

import java.util.ArrayList;
import java.util.List;

import jdk.jfr.Recording;
import jdk.test.lib.jfr.SimpleEventHelper;

/**
 * @test
 * @summary Enable, disable, enable event during recording.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.event.TestReEnableClass
 */
public class TestReEnableClass {

    public static void main(String[] args) throws Throwable {
        test(false);
        test(true);
    }

    // Loop and enable/disable events.
    // Verify recording only contains event created during enabled.
    private static void test(boolean isEnabled) throws Exception {
        System.out.println("Start with isEnabled = " + isEnabled);

        List<Integer> expectedIds = new ArrayList<>();
        Recording r = new Recording();
        SimpleEventHelper.enable(r, isEnabled);
        r.start();

        for (int i = 0; i < 10; ++i) {
            SimpleEventHelper.createEvent(i);
            if (isEnabled) {
                expectedIds.add(i);
            }
            isEnabled = !isEnabled;
            SimpleEventHelper.enable(r, isEnabled);
        }

        r.stop();
        SimpleEventHelper.createEvent(100);

        int[] ids = new int[expectedIds.size()];
        for (int i = 0; i < expectedIds.size(); ++i) {
            ids[i] = expectedIds.get(i);
        }
        SimpleEventHelper.verifyEvents(r, ids);

        r.close();
    }

}
