/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * @test
 * @bug 8292275
 * @summary Test required flags on parameters
 * @compile RequiredMethodParameterFlagTest.java
 * @run junit RequiredMethodParameterFlagTest
 */
class RequiredMethodParameterFlagTest {

    private static final Set<AccessFlag> CHECKED_FLAGS = Set.of(AccessFlag.MANDATED, AccessFlag.SYNTHETIC);

    static Stream<Arguments> testCases() throws ReflectiveOperationException {
        Set<AccessFlag> mandated = Set.of(AccessFlag.MANDATED);
        Set<AccessFlag> synthetic = Set.of(AccessFlag.SYNTHETIC);

        return Stream.of(
                // test for implicit parameters
                // inner class
                Arguments.of(Outer.Inner.class.getDeclaredConstructors()[0],
                        List.of(mandated, Set.of())),
                // anonymous class extending an inner class
                Arguments.of(Class.forName("Outer$1")
                                .getDeclaredConstructors()[0],
                        List.of(mandated, Set.of(), Set.of())),
                // anonymous class
                Arguments.of(Class.forName("Outer$2")
                                .getDeclaredConstructors()[0],
                        List.of(mandated)),
                // enum class
                Arguments.of(Outer.MyEnum.class.getDeclaredMethod("valueOf", String.class),
                        List.of(mandated)),
                // record class
                Arguments.of(Outer.MyRecord.class.getDeclaredConstructors()[0],
                        List.of(mandated, mandated)),
                // local class
                Arguments.of(Class.forName("Outer$1Task")
                                .getDeclaredConstructors()[0],
                        List.of(mandated, Set.of(), synthetic)),
                // test for synthetic parameters
                // assuming javac creates two synthetic parameters corresponding to
                // Enum(String name, int ordinal)
                Arguments.of(Outer.MyEnum.class.getDeclaredConstructors()[0],
                        List.of(synthetic, synthetic, Set.of(), Set.of()))
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void check(Executable method, List<Set<AccessFlag>> paramFlags) {
        Parameter[] parameters = method.getParameters();
        assertEquals(paramFlags.size(), parameters.length, () -> "Parameter count of " + method);

        for (int i = 0; i < parameters.length; i++) {
            Set<AccessFlag> expected = new HashSet<>(paramFlags.get(i));
            expected.retainAll(CHECKED_FLAGS);
            Set<AccessFlag> found = new HashSet<>(parameters[i].accessFlags());
            found.retainAll(CHECKED_FLAGS);
            final int index = i;
            assertEquals(expected, found, () -> "Parameter " + index + " in " + method);
        }
    }
}

// keep this in sync with test/langtools/tools/javac/RequiredParameterFlags/ImplicitParameters.java
class Outer {
    class Inner {
        public Inner(Inner notMandated) {}
    }

    Inner anonymousInner = this.new Inner(null) {};

    Object anonymous = new Object() {};

    private void instanceMethod(int i) {
        class Task implements Runnable {
            final int j;

            Task(int j) {
                this.j = j;
            }

            @Override
            public void run() {
                System.out.println(Outer.this.toString() + (i * j));
            }
        }

        new Task(5).run();
    }

    enum MyEnum {
        ;
        MyEnum(String s, int i) {}
    }

    record MyRecord(int a, Object b) {
        MyRecord {}
    }
}
