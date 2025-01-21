/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8242451
 * @library /test/lib
 * @summary Test that the LAMBDA_INSTANCE$ field is present depending
 *          on disableEagerInitialization
 * @run main LambdaEagerInitTest
 * @run main/othervm -Djdk.internal.lambda.disableEagerInitialization=true LambdaEagerInitTest
 */

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static jdk.test.lib.Asserts.*;

public class LambdaEagerInitTest {

    interface H {Object m(String s);}

    private static Set<String> allowedStaticFields(boolean nonCapturing) {
        Set<String> s = new HashSet<>();
        if (Boolean.getBoolean("jdk.internal.lambda.disableEagerInitialization")) {
            if (nonCapturing) s.add("LAMBDA_INSTANCE$");
        }
        return s;
    }

    private void nonCapturingLambda() {
        H la = s -> s;
        assertEquals("hi", la.m("hi"));
        Class<? extends H> c1 = la.getClass();
        verifyLambdaClass(la.getClass(), true);
    }

    private void capturingLambda() {
        H la = s -> concat(s, "foo");
        assertEquals("hi foo", la.m("hi"));
        verifyLambdaClass(la.getClass(), false);
    }

    private void verifyLambdaClass(Class<?> c, boolean nonCapturing) {
        Set<String> staticFields = new HashSet<>();
        Set<String> instanceFields = new HashSet<>();
        for (Field f : c.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                staticFields.add(f.getName());
            } else {
                instanceFields.add(f.getName());
            }
        }
        assertEquals(instanceFields.size(), nonCapturing ? 0 : 1, "Unexpected instance fields");
        assertEquals(staticFields, allowedStaticFields(nonCapturing), "Unexpected static fields");
    }

    private String concat(String... ss) {
        return Arrays.stream(ss).collect(Collectors.joining(" "));
    }

    public static void main(String[] args) {
        LambdaEagerInitTest test = new LambdaEagerInitTest();
        test.nonCapturingLambda();
        test.capturingLambda();
    }
}
