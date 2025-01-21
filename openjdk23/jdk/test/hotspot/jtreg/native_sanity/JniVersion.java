/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

 /*
 * @test
 * @run main/native JniVersion
 */
public class JniVersion {

    public static final int JNI_VERSION_24 = 0x00180000;

    public static void main(String... args) throws Exception {
        System.loadLibrary("JniVersion");
        int res = getJniVersion();
        if (res != JNI_VERSION_24) {
            throw new Exception("Unexpected value returned from getJniVersion(): 0x" + Integer.toHexString(res));
        }
    }

    static native int getJniVersion();
}
