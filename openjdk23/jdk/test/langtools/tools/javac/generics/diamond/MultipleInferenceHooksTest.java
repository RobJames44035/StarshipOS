/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8062373
 * @summary Test that <>(){} works fine without verify error when there are multiple post inference hooks.
 */

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class MultipleInferenceHooksTest {
    public static void main(String[] args) {
        Set<String> result = Collections.newSetFromMap(new IdentityHashMap<>() {});
    }
}
