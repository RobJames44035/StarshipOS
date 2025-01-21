/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

/**
 * @test
 * @bug 8071667
 * @summary Ensure that ConcurrentModificationExceptions are thrown as specified from Map methods that accept Functions
 * @author bchristi
 * @build Defaults
 * @run testng FunctionalCMEs
 */
public class FunctionalCMEs {
    static final String KEY = "key";

    @DataProvider(name = "Maps", parallel = true)
    private static Iterator<Object[]> makeMaps() {
        return Arrays.asList(
                // Test maps that CME
                new Object[]{new HashMap<>(), true},
                new Object[]{new Hashtable<>(), true},
                new Object[]{new LinkedHashMap<>(), true},
                new Object[]{new TreeMap<>(), true},
                // Test default Map methods - no CME
                new Object[]{new Defaults.ExtendsAbstractMap<>(), false}
        ).iterator();
    }

    @Test(dataProvider = "Maps")
    public void testComputeIfAbsent(Map<String,String> map, boolean expectCME) {
        checkCME(() -> {
            map.computeIfAbsent(KEY, k -> {
                putToForceRehash(map);
                return "computedValue";
            });
        }, expectCME);
    }

    @Test(dataProvider = "Maps")
    public void testCompute(Map<String,String> map, boolean expectCME) {
        checkCME(() -> {
            map.compute(KEY, mkBiFunc(map));
        }, expectCME);
    }

    @Test(dataProvider = "Maps")
    public void testComputeWithKeyMapped(Map<String,String> map, boolean expectCME) {
        map.put(KEY, "firstValue");
        checkCME(() -> {
            map.compute(KEY, mkBiFunc(map));
        }, expectCME);
    }

    @Test(dataProvider = "Maps")
    public void testComputeIfPresent(Map<String,String> map, boolean expectCME) {
        map.put(KEY, "firstValue");
        checkCME(() -> {
           map.computeIfPresent(KEY, mkBiFunc(map));
        }, expectCME);
    }

    @Test(dataProvider = "Maps")
    public void testMerge(Map<String,String> map, boolean expectCME) {
        map.put(KEY, "firstValue");
        checkCME(() -> {
            map.merge(KEY, "nextValue", mkBiFunc(map));
        }, expectCME);
    }

    @Test(dataProvider = "Maps")
    public void testForEach(Map<String,String> map, boolean ignored) {
        checkCME(() -> {
            map.put(KEY, "firstValue");
            putToForceRehash(map);
            map.forEach((k,v) -> {
                map.remove(KEY);
            });
        }, true);
    }

    @Test(dataProvider = "Maps")
    public void testReplaceAll(Map<String,String> map, boolean ignored) {
        checkCME(() -> {
            map.put(KEY, "firstValue");
            putToForceRehash(map);
            map.replaceAll((k,v) -> {
                map.remove(KEY);
                return "computedValue";
            });
        },true);
    }

    private static void checkCME(Runnable code, boolean expectCME) {
        try {
            code.run();
        } catch (ConcurrentModificationException cme) {
            if (expectCME) { return; } else { throw cme; }
        }
        if (expectCME) {
            throw new RuntimeException("Expected CME, but wasn't thrown");
        }
    }

    private static BiFunction<String,String,String> mkBiFunc(Map<String,String> map) {
        return (k,v) -> {
            putToForceRehash(map);
            return "computedValue";
        };
    }

    private static void putToForceRehash(Map<String,String> map) {
        for (int i = 0; i < 64; ++i) {
            map.put(i + "", "value");
        }
    }
}
