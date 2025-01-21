/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8266670 8293626
 * @summary Basic tests of AccessFlag
 */

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.EnumSet;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Set;

public class BasicAccessFlagTest {
    public static void main(String... args) throws Exception {
        testSourceModifiers();
        testMaskOrdering();
        testDisjoint();
        testMaskToAccessFlagsPositive();
        testLocationsNullHandling();
    }

    /*
     * Verify sourceModifier() == true access flags have a
     * corresponding constant in java.lang.reflect.Modifier.
     */
    private static void testSourceModifiers() throws Exception {
        Class<?> modifierClass = Modifier.class;

        for(AccessFlag accessFlag : AccessFlag.values()) {
            if (accessFlag.sourceModifier()) {
                // Check for consistency
                Field f = modifierClass.getField(accessFlag.name());
                if (accessFlag.mask() != f.getInt(null) ) {
                    throw new RuntimeException("Unexpected mask for " +
                                               accessFlag);
                }
            }
        }
    }

    // The mask values of the enum constants must be non-decreasing;
    // in other words stay the same (for colliding mask values) or go
    // up.
    private static void testMaskOrdering() {
        AccessFlag[] values = AccessFlag.values();
        for (int i = 1; i < values.length; i++) {
            AccessFlag left  = values[i-1];
            AccessFlag right = values[i];
            if (left.mask() > right.mask()) {
                throw new RuntimeException(left
                                           + "has a greater mask than "
                                           + right);
            }
        }
    }

    // Test that if access flags have a matching mask, their locations
    // are disjoint.
    private static void testDisjoint() {
        // First build the mask -> access flags map...
        Map<Integer, Set<AccessFlag>> maskToFlags = new LinkedHashMap<>();

        for (var accessFlag : AccessFlag.values()) {
            Integer mask = accessFlag.mask();
            Set<AccessFlag> flags = maskToFlags.get(mask);

            if (flags == null ) {
                flags = new HashSet<>();
                flags.add(accessFlag);
                maskToFlags.put(mask, flags);
            } else {
                flags.add(accessFlag);
            }
        }

        // ...then test for disjointness
        for (var entry : maskToFlags.entrySet()) {
            var value = entry.getValue();
            if (value.size() == 0) {
                throw new AssertionError("Bad flag set " + entry);
            } else if (value.size() == 1) {
                // Need at least two flags to be non-disjointness to
                // be possible
                continue;
            }

            Set<AccessFlag.Location> locations = new HashSet<>();
            for (var accessFlag : value) {
                for (var location : accessFlag.locations()) {
                    boolean added = locations.add(location);
                    if (!added) {
                        reportError(location, accessFlag,
                                    entry.getKey(), value);
                    }
                }
            }
        }
    }

    private static void reportError(AccessFlag.Location location,
                                    AccessFlag accessFlag,
                                    Integer mask, Set<AccessFlag> value) {
        System.err.println("Location " + location +
                           " from " + accessFlag +
                           " already present for 0x" +
                           Integer.toHexString(mask) + ": " + value);
        throw new RuntimeException();
    }

    // For each access flag, make sure it is recognized on every kind
    // of location it can apply to
    private static void testMaskToAccessFlagsPositive() {
        for (var accessFlag : AccessFlag.values()) {
            Set<AccessFlag> expectedSet = EnumSet.of(accessFlag);
            for (var location : accessFlag.locations()) {
                Set<AccessFlag> computedSet =
                    AccessFlag.maskToAccessFlags(accessFlag.mask(), location);
                if (!expectedSet.equals(computedSet)) {
                    throw new RuntimeException("Bad set computation on " +
                                               accessFlag + ", " + location);
                }
            }
        }
    }

    private static void testLocationsNullHandling() {
        for (var flag : AccessFlag.values() ) {
            try {
                flag.locations(null);
                throw new RuntimeException("Did not get NPE on " + flag + ".location(null)");
            } catch (NullPointerException npe ) {
                ; // Expected
            }
        }
    }
}
