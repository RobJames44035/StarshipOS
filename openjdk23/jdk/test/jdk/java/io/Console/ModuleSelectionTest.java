/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8295803 8299689
 * @summary Tests System.console() returns correct Console (or null) from the expected
 *          module.
 * @modules java.base/java.io:+open
 * @run main/othervm ModuleSelectionTest jdk.internal.le
 * @run main/othervm -Djdk.console=jdk.internal.le ModuleSelectionTest jdk.internal.le
 * @run main/othervm -Djdk.console=java.base ModuleSelectionTest java.base
 * @run main/othervm --limit-modules java.base ModuleSelectionTest java.base
 */

import java.io.Console;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ModuleSelectionTest {
    public static void main(String... args) throws Throwable {
        var con = System.console();
        var pc = Class.forName("java.io.ProxyingConsole");
        var jdkc = Class.forName("jdk.internal.io.JdkConsole");
        var istty = (boolean)MethodHandles.privateLookupIn(Console.class, MethodHandles.lookup())
                .findStatic(Console.class, "istty", MethodType.methodType(boolean.class))
                .invoke();
        var impl = con != null ? MethodHandles.privateLookupIn(pc, MethodHandles.lookup())
                .findGetter(pc, "delegate", jdkc)
                .invoke(con) : null;

        var expected = switch (args[0]) {
            case "java.base" -> istty ? "java.base" : "null";
            default -> args[0];
        };
        var actual = con == null ? "null" : impl.getClass().getModule().getName();

        if (!actual.equals(expected)) {
            throw new RuntimeException("""
                Console implementation is not the expected one.
                Expected: %s
                Actual: %s
                """.formatted(expected, actual));
        } else {
            System.out.printf("%s is the expected implementation. (tty: %s)\n", impl, istty);
        }
    }
}
