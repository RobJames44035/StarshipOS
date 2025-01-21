/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import static java.lang.annotation.ElementType.TYPE_USE;
import java.lang.annotation.Target;

class CrashOnNonExistingMethodTest {
    @Target(TYPE_USE)
    @interface Nullable {}

    static <T extends @Nullable Object> T identity(T t) {
        return t;
    }

    static void test() {
        CrashOnNonExistingMethodTest.<@Nullable Object>nonNullIdentity(null);
    }
}
