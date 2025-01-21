/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @requires os.arch == "aarch64" & os.family == "mac"
 * @run main/othervm/native TestCodegenAttach
 */

public class TestCodegenAttach {

    static native void testCodegenAttach();

    static {
        System.loadLibrary("codegenAttach");
    }

    public static void main(String[] args) throws Throwable {
        testCodegenAttach();
    }
}
