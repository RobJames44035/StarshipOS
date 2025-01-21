/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8268250
 * @summary Check exceptional behavior of Class.arrayType
 */

import java.lang.reflect.*;
import java.util.function.*;

public class ArrayType {
    public static void main(String... args) {
        expectException(() -> Void.TYPE);
        expectException(() -> Array.newInstance(int.class, new int[255])
                        .getClass());
    }

    private static void expectException(Supplier<Class<?>> arrayTypeArg) {
        try {
            Class<?> arrayClazz = arrayTypeArg.get().arrayType();
            throw new RuntimeException("Expected exception not thrown: " +
                                       arrayClazz);
        } catch (UnsupportedOperationException uoe) {
            ; // Expected
        }
    }
}
