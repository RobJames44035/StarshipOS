/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;

// This class should be loaded from a shared archive.
public class MultiProcClass {
    private static String instanceLabel;

    public static void main(String args[]) throws Exception {
        instanceLabel = args[0];
        String checkPmap = args[1];

        long pid = ProcessHandle.current().pid();
        System.out.println(inst("========================== Starting MultiProcClass"));
        System.out.println(inst("My PID: " + pid ));
        System.out.println(inst("checkPmap = <" + checkPmap + ">" ));

        if ("true".equals(checkPmap)) {
            if (runPmap(pid, true) != 0)
                System.out.println("MultiProcClass: Pmap failed");
        }

        WhiteBox wb = WhiteBox.getWhiteBox();
        if (!wb.isSharedClass(MultiProcClass.class)) {
            throw new RuntimeException(inst("MultiProcClass should be shared but is not."));
        }

        System.out.println(inst("========================== Leaving MultiProcClass"));
    }

    // A convenience method to append process instance label
    private static String inst(String msg) {
        return "process-" + instanceLabel + " : " + msg;
    }

    // Use on Linux-only; requires jdk-9 for Process.pid()
    public static int runPmap(long pid, boolean inheritIO) throws Exception {
        ProcessBuilder pmapPb = new ProcessBuilder("pmap", "" + pid);
        if (inheritIO)
            pmapPb.inheritIO();

        return pmapPb.start().waitFor();
    }
}
