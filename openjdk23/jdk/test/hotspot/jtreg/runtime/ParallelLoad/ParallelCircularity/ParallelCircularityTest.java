/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 4699981 8295278
 * @summary This is a testcase for JDK-4699981. When 2 threads are loading the same class with
 *           the same classloader, and somehow one of the 2 threads releases the
 *           synchronization lock on the classloader, the JVM code
 *           throws ClassCircularityError, mistakenly.
 * @library /test/lib
 * @compile test-classes/Base.java test-classes/Derived.java test-classes/Support.java
 * @run main/othervm ParallelCircularityTest
 */

import java.net.URL;
import java.net.URLClassLoader;

public class ParallelCircularityTest {

    private Object lock = new Object();

    private void test() throws Exception {
       URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
       URLLoader loader = new URLLoader(new URL[] {location}, getClass().getClassLoader().getParent());

       Class cls = loader.loadClass("Support");

       Thread t1 = new Thread(new Run1(cls));
       t1.start();

       Thread.sleep(1000);

       // Load Derived, will trigger a loadClassInternal for Base
       loader.loadClass("Derived");
    }

    public static void main(String[] args) throws Exception {
        ParallelCircularityTest pct = new ParallelCircularityTest();
        pct.test();
    }

    public class URLLoader extends URLClassLoader {
       private boolean m_firstTime = true;

       public URLLoader(URL[] urls, ClassLoader parent) {
          super(urls, parent);
       }

       public Class loadClass(String name) throws ClassNotFoundException {
          if (name.equals("Base")) {
             if (m_firstTime) {
                m_firstTime = false;

                // Notify the other thread
                synchronized (lock) {
                   lock.notifyAll();
                }

                // Wait on the classloader to have the JVM throw ClassCircularityError
                try {
                   synchronized (this) {
                      wait(5000);
                   }
                }
                catch (InterruptedException ignored) { }
             }
          }
          return super.loadClass(name);
       }
    }

    public class Run1 implements Runnable {
       private Class cls;

       public Run1(Class cls) {
          this.cls = cls;
       }

       public void run() {
          synchronized (lock) {
             try {
                lock.wait();
             }
             catch (InterruptedException ignored) {}
          }

          // Trigger loadClassInternal for Base
          try {
             cls.newInstance();
          } catch (Throwable x) {
             x.printStackTrace();
          }
       }
    }
}
