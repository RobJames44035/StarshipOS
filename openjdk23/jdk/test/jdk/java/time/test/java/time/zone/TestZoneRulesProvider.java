/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package test.java.time.zone;

import java.time.ZoneId;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.time.zone.ZoneRulesProvider;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.NavigableMap;
import java.util.Set;

import org.testng.annotations.Test;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * @summary Tests for ZoneRulesProvider class.
 * @bug 8299571 8302983
 */
@Test
public class TestZoneRulesProvider {

    /**
     * Tests whether partially registered zones are cleaned on a provider registration
     * failure, in case a duplicated zone is detected.
     * @bug 8299571
     */
    @Test
    public void test_registerDuplicatedZone() {
        Set<String> myZoneIds = new LinkedHashSet<>(Arrays.asList(new String[] {"MyID_1", "MyID_2", "CET", "MyID_3"}));
        try {
            ZoneRulesProvider.registerProvider(new IdsOnlyZoneRulesProvider(myZoneIds));
            throw new RuntimeException("Registering a provider that duplicates a zone should throw an exception");
        } catch (ZoneRulesException e) {
            // Ignore. Failure on registration is expected.
        }

        myZoneIds.stream().forEach(id -> {
            var isCET = id.equals("CET");

            // availability check
            var available = ZoneId.getAvailableZoneIds().contains(id);
            if (available ^ isCET) {
                throw new RuntimeException("Unexpected availability for " + id + ", availability: " + available);
            }

            // instantiation check
            try {
                ZoneId.of(id);
                assertTrue(isCET, "ZoneId.of() for the custom id %s should throw ZoneRulesException.".formatted(id));
            } catch (ZoneRulesException e) {
                assertFalse(isCET, "Not possible to obtain a ZoneId for \"CET\".");
            }
        });
    }

    /**
     * Tests whether registering a provider twice will still leave it registered.
     * @bug 8302983
     */
    @Test
    public void test_registerTwice() {
        String zone = "MyID";
        var provider = new IdsOnlyZoneRulesProvider(Set.of(zone));
        assertFalse(ZoneId.getAvailableZoneIds().contains(zone), "Unexpected availability for " + zone);
        ZoneRulesProvider.registerProvider(provider);
        assertTrue(ZoneId.getAvailableZoneIds().contains(zone), "Unexpected non-availability for " + zone);
        try {
            ZoneId.of(zone);
        } catch (ZoneRulesException e) {
            fail("ZoneId instance for " + zone + " should be obtainable");
        }

        try {
            ZoneRulesProvider.registerProvider(provider);
            fail("Registering an already registered provider should throw an exception");
        } catch (ZoneRulesException e) {
            // Ignore. Failure on duplicate registration is expected.
        }

        // availability check
        assertTrue(ZoneId.getAvailableZoneIds().contains(zone), "Unexpected non-availability for " + zone);
        // instantiation check
        try {
            ZoneId.of(zone);
        } catch (ZoneRulesException e) {
            fail("ZoneId instance for " + zone + " should still be obtainable", e);
        }
    }

    private static class IdsOnlyZoneRulesProvider extends ZoneRulesProvider {

        private final Set<String> zones;

        IdsOnlyZoneRulesProvider(Set<String> zones) {
            this.zones = zones;
        }

        @Override
        protected Set<String> provideZoneIds() {
            return zones;
        }

        @Override
        protected ZoneRules provideRules(String zoneId, boolean forCaching) {
            return null;
        }

        @Override
        protected NavigableMap<String, ZoneRules> provideVersions(String zoneId) {
            return null;
        }
    }
}
