/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadFactory;

public class DebuggeeWrapper {

    public static String PROPERTY_NAME = "test.thread.factory";

    private static final String OLD_MAIN_THREAD_NAME = "old-m-a-i-n";

    private static ThreadFactory threadFactory = r -> new Thread(r);

    private static final String testThreadFactoryName = System.getProperty(PROPERTY_NAME);

    public static String getTestThreadFactoryName() {
        return testThreadFactoryName;
    }

    public static boolean isVirtual() {
        return "Virtual".equals(testThreadFactoryName);
    }

    public static Thread newThread(Runnable task) {
        return threadFactory.newThread(task);
    }

    public static Thread newThread(Runnable task, String name) {
        Thread t = newThread(task);
        t.setName(name);
        return t;
    }

    public static void main(String[] args) throws Throwable {
        String className = args[0];
        String[] classArgs = new String[args.length - 1];
        System.arraycopy(args, 1, classArgs, 0, args.length - 1);
        Class c = Class.forName(className);
        java.lang.reflect.Method mainMethod = c.getMethod("main", new Class[] { String[].class });
        mainMethod.setAccessible(true);

        if (isVirtual()) {
            threadFactory = Thread.ofVirtual().factory();
            MainThreadGroup tg = new MainThreadGroup();
            Thread vthread = Thread.ofVirtual().unstarted(() -> {
                try {
                    mainMethod.invoke(null, new Object[] { classArgs });
                } catch (InvocationTargetException e) {
                    tg.uncaughtThrowable = e.getCause();
                } catch (Throwable error) {
                    tg.uncaughtThrowable = error;
                }
            });
            Thread.currentThread().setName(OLD_MAIN_THREAD_NAME);
            vthread.setName("main");
            vthread.start();
            vthread.join();
            if (tg.uncaughtThrowable != null) {
                // Note we cant just rethrow tg.uncaughtThrowable because there are tests
                // that track ExceptionEvents, and they will complain about the extra
                // exception. So instead mimic what happens when the main thread exits
                // with an exception.
                System.out.println("Uncaught Exception: " + tg.uncaughtThrowable);
                tg.uncaughtThrowable.printStackTrace(System.out);
                System.exit(1);
            }
        } else if (getTestThreadFactoryName().equals("Kernel")) {
            MainThreadGroup tg = new MainThreadGroup();
            Thread t = new Thread(tg, () -> {
                try {
                    mainMethod.invoke(null, new Object[] { classArgs });
                } catch (InvocationTargetException e) {
                    tg.uncaughtThrowable = e.getCause();
                } catch (Throwable error) {
                    tg.uncaughtThrowable = error;
                }
            });
            t.start();
            t.join();
            if (tg.uncaughtThrowable != null) {
                throw new RuntimeException(tg.uncaughtThrowable);
            }
        } else {
            mainMethod.invoke(null, new Object[] { classArgs });
        }
    }

    static class MainThreadGroup extends ThreadGroup {
        MainThreadGroup() {
            super("MainThreadGroup");
        }

        public void uncaughtException(Thread t, Throwable e) {
            if (e instanceof ThreadDeath) {
                return;
            }
            e.printStackTrace(System.err);
            uncaughtThrowable = e;
        }
        Throwable uncaughtThrowable = null;
    }
}