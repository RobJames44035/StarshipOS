/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8239083
 * @summary Test invocation of static interface method with and without method handle with C1.
 *
 * @run main/othervm -Xbatch -XX:TieredStopAtLevel=3 compiler.c1.TestStaticInterfaceMethodCall
 */

package compiler.c1;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TestStaticInterfaceMethodCall {

     static final MethodHandle MH_m;

     static {
         try {
             MH_m = MethodHandles.lookup().findStatic(MyInterface.class, "m", MethodType.methodType(void.class));
         } catch (ReflectiveOperationException e) {
             throw new BootstrapMethodError(e);
         }
     }

     public static void main(String[] args) throws Throwable {
         for (int i = 0; i < 20_000; i++) {
             test_call_by_method_handle();
             test_direct_call();
         }
     }

     static void test_call_by_method_handle() throws Throwable {
         MH_m.invokeExact();
     }

     static void test_direct_call() {
         MyInterface.m();
     }

}

interface MyInterface {
     static void m() {}
}
