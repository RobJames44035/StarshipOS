/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package nsk.share;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;


public final class MainWrapper {
    public static final String OLD_MAIN_THREAD_NAME = "old-m-a-i-n";

    static AtomicReference<Throwable> ue = new AtomicReference<>();
    public MainWrapper() {
    }

    public static void main(String[] args) throws Throwable {
        String wrapperName = args[0];
        String className = args[1];
        String[] classArgs = new String[args.length - 2];
        System.arraycopy(args, 2, classArgs, 0, args.length - 2);

        // Some tests use this property to understand if virtual threads are used
        System.setProperty("test.thread.factory", wrapperName);

        Runnable task = () -> {
            try {
                Class<?> c = Class.forName(className);
                Method mainMethod = c.getMethod("main", new Class[] { String[].class });
                mainMethod.setAccessible(true);
                mainMethod.invoke(null, new Object[] { classArgs });
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                ue.set(e.getCause());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread t;
        if (wrapperName.equals("Virtual")) {
            t = unstartedVirtualThread(task);
        } else {
            t = new Thread(task);
        }
        t.setName("main");
        Thread.currentThread().setName(OLD_MAIN_THREAD_NAME);
        t.start();
        t.join();
        if (ue.get() != null) {
            throw ue.get();
        }
    }

    static Thread unstartedVirtualThread(Runnable task) {
        return Thread.ofVirtual().unstarted(task);
    }

}
