/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * Simple application that checks that shutdown does not commence until
 * after the last non-daemon thread has terminated. Reporting failure is
 * tricky because we can't uses exceptions (as they are ignored from Shutdown
 * hooks) and we can't call System.exit. So we rely on System.out being checked
 * in the TestDaemonDestroy application.
 */
public class Main {

    static volatile Thread t1;

    public static void main() {
        t1 = new Thread(() -> {
                System.out.println("T1 started");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) { }
                System.out.println("T1 finished");
            }, "T1");

        t1.setDaemon(false);

        Thread hook = new Thread(() -> {
                System.out.println("HOOK started");
                if (t1.isAlive()) {
                    System.out.println("Error: T1 isAlive");
                }
            }, "HOOK");
        Runtime.getRuntime().addShutdownHook(hook);
        t1.start();
    }
}
