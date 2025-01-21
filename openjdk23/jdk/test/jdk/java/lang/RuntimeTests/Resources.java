/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 4349850
 * @summary java.lang.Runtime.maxMemory()/availableProcessors()
 */

import java.io.PrintStream;

public class Resources {

    static PrintStream out = System.err;

    public static void main(String args[]) throws Exception {
        Runtime rt = Runtime.getRuntime();
        out.println(rt.freeMemory() + " bytes free");
        out.println(rt.totalMemory() + "  bytes in use");
        out.println(rt.maxMemory() + " bytes max");
        out.println(rt.availableProcessors() + " processors");
    }

}
