/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 6312706
 * @summary Sets from Map.entrySet() return distinct objects for each Entry
 * @author Neil Richards <neil.richards@ngmr.net>, <neil_richards@uk.ibm.com>
 */

import java.util.IdentityHashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DistinctEntrySetElements {
    public static void main(String[] args) throws Exception {
        final IdentityHashMap<String, String> identityHashMap =
            new IdentityHashMap<>();

        identityHashMap.put("One", "Un");
        identityHashMap.put("Two", "Deux");
        identityHashMap.put("Three", "Trois");

        Set<Map.Entry<String, String>> entrySet = identityHashMap.entrySet();
        HashSet<Map.Entry<String, String>> hashSet = new HashSet<>(entrySet);

        // NB: These comparisons are valid in this case because none of the
        //     keys put into 'identityHashMap' above are equal to any other.
        if (false == hashSet.equals(entrySet)) {
            throw new RuntimeException("Test FAILED: Sets are not equal.");
        }
        if (hashSet.hashCode() != entrySet.hashCode()) {
            throw new RuntimeException("Test FAILED: Set's hashcodes are not equal.");
        }
    }
}
