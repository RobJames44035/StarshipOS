/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * Class1 loads a native library.
 */
package p;

public class Class1 {

    public Class1() {
    }

    // method called from java threads
    public void loadLibrary(Object obj) throws Exception {
        System.loadLibrary("loadLibraryUnload");
        System.out.println("Native library loaded from Class1.");
        synchronized (Class1.class) {
            setRef(obj);
        }
    }

    /**
     * Native method to store an object ref in a native Global reference
     * to be cleared when the library is unloaded.
     * @param obj an object
     */
    private static native void setRef(Object obj);
}
