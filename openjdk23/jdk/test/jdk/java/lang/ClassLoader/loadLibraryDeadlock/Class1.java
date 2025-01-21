/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * Class1 loads a native library that calls ClassLoader.findClass in JNI_OnLoad.
 * Class1 runs concurrently with another thread that opens a signed jar file.
 */
class Class1 {
    static {
        System.loadLibrary("loadLibraryDeadlock");
        System.out.println("Signed jar loaded from native library.");
    }
}
