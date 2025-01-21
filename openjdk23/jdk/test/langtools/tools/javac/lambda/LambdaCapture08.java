/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8321207
 * @summary javac is not accepting correct code
 * @compile LambdaCapture08.java
 */

import java.util.function.*;

interface LambdaCapture08 {
    Object O = new Object() {
        IntSupplier x(int m) {
            return () -> m;
        }
    };
}
