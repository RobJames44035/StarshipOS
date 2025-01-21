/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

public class TrySwitchMyLoader {
    public static void main(String args[]) {
        System.out.println("TrySwitchMyLoader's loader = " + ReportMyLoader.class.getClassLoader());
        System.setProperty("java.system.class.loader", "TestClassLoader");

        // This should still report the same loader as TrySwitchMyLoader.class.getClassLoader(),
        // as setting the java.system.class.loader after main method has been executed
        // has no effect.
        ReportMyLoader.main(args);
    }
}

