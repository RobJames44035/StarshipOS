/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 6199320
 * @summary Properties.store() causes deadlock when concurrently calling TimeZone apis
 * @run main/timeout=20 StoreDeadlock
 * @author Xueming Shen
 */

import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;

public class StoreDeadlock {
    public StoreDeadlock() {
        Properties sysproperty = System.getProperties();
        Thread1 t1 = new Thread1(sysproperty);
        Thread2 t2 = new Thread2();
        t1.start();
        t2.start();
    }
    public static void main(String[] args) {
        StoreDeadlock deadlock = new StoreDeadlock();
    }
    class Thread1 extends Thread {
        Properties sp;
        public Thread1(Properties p) {
            sp = p;
        }
        public void run() {
            try {
                sp.store(System.out, null);
            } catch (IOException e) {
                System.out.println("IOException : " + e);
            }
        }
    }
    class Thread2 extends Thread {
        public void run() {
            System.out.println("tz=" + TimeZone.getTimeZone("PST"));
        }
    }
}
