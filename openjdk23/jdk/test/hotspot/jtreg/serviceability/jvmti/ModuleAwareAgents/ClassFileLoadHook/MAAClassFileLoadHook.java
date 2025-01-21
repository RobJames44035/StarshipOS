/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @requires vm.jvmti
 * @run main/othervm/native -agentlib:MAAClassFileLoadHook MAAClassFileLoadHook
 * @run main/othervm/native -agentlib:MAAClassFileLoadHook=with_early_vmstart MAAClassFileLoadHook
 * @run main/othervm/native -agentlib:MAAClassFileLoadHook=with_early_class_hook MAAClassFileLoadHook
 * @run main/othervm/native -agentlib:MAAClassFileLoadHook=with_early_vmstart,with_early_class_hook MAAClassFileLoadHook
 */

public class MAAClassFileLoadHook {

    static {
        try {
            System.loadLibrary("MAAClassFileLoadHook");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load MAAClassFileLoadHook library");
            System.err.println("java.library.path: "
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check();

    public static void main(String args[]) {
        int status = check();
        if (status != 0) {
            throw new RuntimeException("Non-zero status returned from the agent: " + status);
        }
    }
}
