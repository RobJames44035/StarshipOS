/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * @test
 * @bug 8271623
 *
 * @compile --release 17 DontOptimizeOuterThis.java InnerClasses.java
 * @run main DontOptimizeOuterThis
 */
public class DontOptimizeOuterThis extends InnerClasses {

    public static void main(String[] args) {
        new DontOptimizeOuterThis().test();
    }

    public void test() {
        checkInner(localCapturesParameter(0), true);
        checkInner(localCapturesLocal(), true);
        checkInner(localCapturesEnclosing(), true);

        checkInner(anonCapturesParameter(0), true);
        checkInner(anonCapturesLocal(), true);
        checkInner(anonCapturesEnclosing(), true);

        checkInner(StaticMemberClass.class, false); // static
        checkInner(NonStaticMemberClass.class, true);
        checkInner(NonStaticMemberClassCapturesEnclosing.class, true);

        checkInner(N0.class, false); // static
        checkInner(N0.N1.class, true);
        checkInner(N0.N1.N2.class, true);
        checkInner(N0.N1.N2.N3.class, true);
        checkInner(N0.N1.N2.N3.N4.class, true);
        checkInner(N0.N1.N2.N3.N4.N5.class, true);

        checkInner(SerializableCapture.class, true);
        checkInner(SerializableWithSerialVersionUID.class, true);
        checkInner(SerializableWithInvalidSerialVersionUIDType.class, true);
        checkInner(SerializableWithInvalidSerialVersionUIDNonFinal.class, true);
        checkInner(SerializableWithInvalidSerialVersionUIDNonStatic.class, true);
    }

    private static void checkInner(Class<?> clazz, boolean expectOuterThis) {
        Optional<Field> outerThis = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getName().startsWith("this$")).findFirst();
        if (expectOuterThis) {
            if (outerThis.isEmpty()) {
                throw new AssertionError(
                        String.format(
                                "expected %s to have an enclosing instance", clazz.getName()));
            }
        } else {
            if (outerThis.isPresent()) {
                throw new AssertionError(
                        String.format("%s had an unexpected enclosing instance", clazz.getName()));
            }
        }
    }
}
