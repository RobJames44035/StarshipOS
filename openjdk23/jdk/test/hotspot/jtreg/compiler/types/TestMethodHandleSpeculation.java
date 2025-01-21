/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8269285
 * @summary Crash/miscompile in CallGenerator::for_method_handle_inline after JDK-8191998
 * @requires vm.compMode == "Xmixed" & vm.flavor == "server"
 *
 * @run main/othervm
 *        -Xcomp -XX:CompileCommand=quiet -XX:CompileCommand=compileonly,compiler.types.TestMethodHandleSpeculation::main
 *        compiler.types.TestMethodHandleSpeculation
 */

package compiler.types;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Supplier;

public class TestMethodHandleSpeculation {

    public static void main(String... args) {
        byte[] serObj = {1};
        MyClass<byte[]> obj = new MyClass<>();
        for (int i = 0; i < 100_000; i++) {
            boolean test = obj.test(serObj);
            if (test) {
                throw new IllegalStateException("Cannot be null");
            }
        }
    }

    static class MyClass<V extends Serializable> {
        boolean test(V obj) {
            Supplier<Boolean> supp = () -> (obj == null);
            return supp.get();
        }
    }

}
