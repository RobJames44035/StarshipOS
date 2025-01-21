/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

// See bug 5094825 / ClassLoadDeadlock.sh

import java.net.*;

import java.security.*;

public class ClassLoaderDeadlock {

    public static void main(String[] args) throws Exception {
        // create a new classloader
        URL url = new URL("file:provider/");
        final DelayClassLoader cl = new DelayClassLoader(url);

        // install a provider on the custom class loader as the most prefered provider
        Class clazz = cl.loadClass("HashProvider");
        Provider p = (Provider)clazz.newInstance();
        Security.insertProviderAt(p, 1);

        // add a delay in class loading for reproducability
        cl.delay = 1000;

        new Thread() {
            public void run() {
                try {
                    // load a random system class
                    // this locks the DelayClassLoader and after the delay the system class loader
                    Class c1 = cl.loadClass("java.lang.String");
                    System.out.println(c1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        // make sure the other thread got a chance to run and grab the lock
        Thread.sleep(200);

        // load a random class from a signed JAR file to trigger JAR signature verification
        // locks system class loader first and then tries to lock DelayClassLoader to load the hashes
        // DEADLOCK HAPPENS HERE
        Class c2 = Class.forName("com.abc.Tst1");

        // NOT REACHED
        System.out.println(c2);

        System.out.println("OK");
    }

    static class DelayClassLoader extends URLClassLoader {

        volatile int delay;

        DelayClassLoader(URL url) {
            super(new URL[] {url});
        }

        protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
            System.out.println("-loadClass(" + name + "," + resolve + ")");
            try {
                // delay so that we hold the lock for a longer period
                // makes it much easier to reproduce
                Thread.sleep(delay);
            } catch (InterruptedException e) { e.printStackTrace(); }
            return super.loadClass(name, resolve);
        }
    }

}
