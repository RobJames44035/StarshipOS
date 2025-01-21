/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test id=default
 * @summary Test virtual thread entering (and reentering) a lot of monitors with no contention
 * @library /test/lib
 * @run main LotsOfUncontendedMonitorEnter
 */

/*
 * @test id=LM_LEGACY
 * @library /test/lib
 * @run main/othervm -XX:LockingMode=1 LotsOfUncontendedMonitorEnter
 */

/*
 * @test id=LM_LIGHTWEIGHT
 * @library /test/lib
 * @run main/othervm -XX:LockingMode=2 LotsOfUncontendedMonitorEnter
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import jdk.test.lib.thread.VThreadRunner;

public class LotsOfUncontendedMonitorEnter {

    public static void main(String[] args) throws Exception {
        int depth;
        if (args.length > 0) {
            depth = Integer.parseInt(args[0]);
        } else {
            depth = 24; // 33554430 enters
        }
        VThreadRunner.run(() -> {
            testEnter(List.of(), depth);
        });
    }

    /**
     * Enter the monitor for a new object, reenter a monitor that is already held, and
     * repeat to the given depth.
     */
    private static void testEnter(List<Object> ownedMonitors, int depthRemaining) {
        if (depthRemaining > 0) {
            var lock = new Object();
            synchronized (lock) {
                // new list of owned monitors
                var monitors = concat(ownedMonitors, lock);
                testEnter(monitors, depthRemaining - 1);

                // reenter a monitor that is already owned
                int index = ThreadLocalRandom.current().nextInt(monitors.size());
                var otherLock = monitors.get(index);

                synchronized (otherLock) {
                    testEnter(monitors, depthRemaining - 1);
                }
            }
        }
    }

    /**
     * Adds an element to a list, returning a new list.
     */
    private static <T> List<T> concat(List<T> list, T object) {
        var newList = new ArrayList<>(list);
        newList.add(object);
        return newList;
    }
}
