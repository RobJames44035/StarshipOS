/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8244573
 * @summary javap, java.lang.ArrayIndexOutOfBoundsException thrown for malformed class file
 * @build Malformed
 * @run main T8244573
 * @modules jdk.jdeps/com.sun.tools.javap
 */
import java.io.PrintWriter;

public class T8244573 {

    public static void main(String args[]) throws Exception {
        if (com.sun.tools.javap.Main.run(new String[]{"-c", System.getProperty("test.classes") + "/Malformed.class"},
                new PrintWriter(System.out)) != 0) {
            throw new AssertionError();
        }
    }
}
