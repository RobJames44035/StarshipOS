/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.util.function.Consumer;
import java.nio.ByteBuffer;

/*
 * @test
 * @bug 8154180
 * @summary Regression: stuck expressions do not behave correctly
 * @compile T8154180a.java
 */
class T8154180a {
    T8154180a(Consumer<ByteBuffer> cb) { }

    public static void main(String[] args) {
        new T8154180a(b -> System.out.println(asString(b)));
        new T8154180a((b -> System.out.println(asString(b))));
        new T8154180a(true ? b -> System.out.println(asString(b)) : b -> System.out.println(asString(b)));
        new T8154180a((true ? b -> System.out.println(asString(b)) : b -> System.out.println(asString(b))));
        new T8154180a((true ? (b -> System.out.println(asString(b))) : (b -> System.out.println(asString(b)))));
    }

    static String asString(ByteBuffer buf) {
        return null;
    }
}
