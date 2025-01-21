/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import static java.lang.invoke.MethodHandles.Lookup.ClassOption.*;

public class LotsUnloadApp implements Runnable {
    static byte[] classdata;
    static int exitAfterNumClasses = 1024;

    public static void main(String args[]) throws Throwable {
        String resname = DefinedAsHiddenKlass.class.getName() + ".class";
        classdata = LotsUnloadApp.class.getClassLoader().getResourceAsStream(resname).readAllBytes();

        int numThreads = 4;
        try {
            numThreads = Integer.parseInt(args[0]);
        } catch (Throwable t) {}

        try {
            exitAfterNumClasses = Integer.parseInt(args[1]);
        } catch (Throwable t) {}

        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(new LotsUnloadApp());
            t.start();
        }
    }

    public void run() {
        while (true) {
            try {
                Lookup lookup = MethodHandles.lookup();
                Class<?> cl = lookup.defineHiddenClass(classdata, false, NESTMATE).lookupClass();
                cl.newInstance();
                add();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    static int n;
    static synchronized void add() {
        n++;
        if (n >= exitAfterNumClasses) {
            System.exit(0);
        }
    }
}

class DefinedAsHiddenKlass {
    // ZGC region size is always a multiple of 2MB on x64.
    // Make this slightly smaller than that.
    static byte[] array = new byte[2 * 1024 * 1024 - 8 * 1024];
    static String x;
    public DefinedAsHiddenKlass() {
        // This will generate some lambda forms hidden classes for string concat.
        x = "array size is "  + array.length + " bytes ";
    }
}
