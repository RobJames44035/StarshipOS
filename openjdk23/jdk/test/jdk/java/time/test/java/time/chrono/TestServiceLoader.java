/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.chrono;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.time.chrono.Chronology;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.testng.annotations.Test;

/**
 * Tests that a custom Chronology is available via the ServiceLoader.
 * The CopticChronology is configured via META-INF/services/java.time.chrono.Chronology.
 */
@Test
public class TestServiceLoader {

    @Test
    public void test_copticServiceLoader() {
        Map<String, Chronology> chronos = new HashMap<>();
        ServiceLoader<Chronology> loader = ServiceLoader.load(Chronology.class, null);
        for (Chronology chrono : loader) {
            chronos.put(chrono.getId(), chrono);
        }
        var coptic = chronos.get("Coptic");
        assertNotNull(coptic, "CopticChronology not found");
        assertEquals(coptic.isIsoBased(), false);
    }

}
