/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.metadata.eventtype;

import java.util.List;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.test.lib.Asserts;

/**
 * @test
 * @summary Test setName().
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.eventtype.TestGetCategory
 */
public class TestGetCategory {

    public static void main(String[] args) throws Throwable {

        List<String> noCategory = EventType.getEventType(NoCategory.class).getCategoryNames();
        System.out.println("noCategory=" + noCategory);
        Asserts.assertEquals(noCategory.size(), 1, "Wrong default category");
        Asserts.assertEquals(noCategory.getFirst(), "Uncategorized", "Wrong default category");

        List<String>  withCategory = EventType.getEventType(WithCategory.class).getCategoryNames();
        Asserts.assertEquals(withCategory.size(), 4, "Wrong category");
        Asserts.assertEquals(withCategory.get(0), "Category", "Wrong category");
        Asserts.assertEquals(withCategory.get(1), "A", "Wrong category");
        Asserts.assertEquals(withCategory.get(2), "B", "Wrong category");
        Asserts.assertEquals(withCategory.get(3), "C", "Wrong category");
    }

    private static class NoCategory extends Event {
        @SuppressWarnings("unused")
        public byte myByte;
    }

    @Category({"Category", "A", "B", "C"})
    private static class WithCategory extends Event {
        @SuppressWarnings("unused")
            public byte myByte;
    }
}
