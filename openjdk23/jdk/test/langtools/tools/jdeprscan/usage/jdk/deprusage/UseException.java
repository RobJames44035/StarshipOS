/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jdeprusage;

import jdk.deprcases.types.DeprecatedException;

public class UseException {
    static class Throws {
        static void foo() throws DeprecatedException { }
    }

    static class Catch {
        void foo() {
            try {
                Throws.foo();
            } catch (DeprecatedException de) { }
        }
    }
}
