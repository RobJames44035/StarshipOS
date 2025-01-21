/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug     8290482
 * @summary Tests that DestroyJavaVM from an active thread fails.
 * @run main/native TestActiveDestroy
 */

public class TestActiveDestroy {

    static native boolean tryDestroyJavaVM();

    static {
        System.loadLibrary("activeDestroy");
    }

    public static void main(String[] args) throws Throwable {
        if (tryDestroyJavaVM()) {
            throw new Error("DestroyJavaVM succeeded when it should not!");
        }
    }
}
