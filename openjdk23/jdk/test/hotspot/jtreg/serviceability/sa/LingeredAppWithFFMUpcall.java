
/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.util.concurrent.CountDownLatch;

import jdk.test.lib.apps.LingeredApp;

public class LingeredAppWithFFMUpcall extends LingeredApp {

    public static final String THREAD_NAME = "Upcall thread";

    private static final Object lockObj = new Object();

    private static final CountDownLatch signal = new CountDownLatch(1);

    static {
        System.loadLibrary("upcall");
    }

    public static void upcall() {
        signal.countDown();
        synchronized(lockObj) {
        }
    }

    public static long createFunctionPointerForUpcall() throws NoSuchMethodException, IllegalAccessException {
        var mh = MethodHandles.lookup()
                              .findStatic(LingeredAppWithFFMUpcall.class, "upcall", MethodType.methodType(void.class));
        var stub = Linker.nativeLinker()
                         .upcallStub(mh, FunctionDescriptor.ofVoid(), Arena.global());
        return stub.address();
    }

    public static native void callJNI(long upcallAddr);

    public static void main(String[] args) {
        try {
            long upcallAddr = createFunctionPointerForUpcall();
            var upcallThread = new Thread(() -> callJNI(upcallAddr), THREAD_NAME);
            synchronized(lockObj) {
                upcallThread.start();
                signal.await();
                LingeredApp.main(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
