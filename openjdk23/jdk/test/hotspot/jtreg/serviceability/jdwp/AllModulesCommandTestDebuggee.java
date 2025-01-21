/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.util.Set;
import java.util.HashSet;

/**
 * The debuggee to be launched by the test
 * Sends out the info about the loaded modules
 * then stays to respond to the JDWP commands
 */
public class AllModulesCommandTestDebuggee {

    public static void main(String[] args) throws InterruptedException {

        int modCount = ModuleLayer.boot().modules().size();

        // Send all modules names via the process output
        for (Module mod : ModuleLayer.boot().modules()) {
            String info = String.format("module %s", mod.getName());
            write(info);
        }
        // Signal that the sending is done
        write("ready");
        Thread.sleep(Long.MAX_VALUE);
    }

    private static void write(String s) {
        System.out.println(s);
        System.out.flush();
    }

}
