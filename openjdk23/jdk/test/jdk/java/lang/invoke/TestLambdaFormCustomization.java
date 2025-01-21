/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;

/**
  * @test
  * @bug 8340812
  * @summary Verify that LambdaForm customization via MethodHandle::updateForm is thread safe.
  * @run main TestLambdaFormCustomization
  * @run main/othervm -Djava.lang.invoke.MethodHandle.CUSTOMIZE_THRESHOLD=0 TestLambdaFormCustomization
  */
public class TestLambdaFormCustomization {

    String str = "test";
    static final String value = "test" + 42;

    // Trigger concurrent LambdaForm customization for VarHandle invokers
    void test() throws NoSuchFieldException, IllegalAccessException {
        VarHandle varHandle = MethodHandles.lookup().in(getClass()).findVarHandle(getClass(), "str", String.class);

        ArrayList<Thread> threads = new ArrayList<>();
        for (int threadIdx = 0; threadIdx < 10; threadIdx++) {
            threads.add(new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    varHandle.compareAndExchange(this, value, value);
                    varHandle.compareAndExchange(this, value, value);
                    varHandle.compareAndExchange(this, value, value);
                }
            }));
        }
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        TestLambdaFormCustomization t = new TestLambdaFormCustomization();
        for (int i = 0; i < 4000; ++i) {
            t.test();
        }
    }
}
